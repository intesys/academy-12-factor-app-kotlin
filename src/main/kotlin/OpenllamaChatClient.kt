package org.example

import com.jayway.jsonpath.JsonPath
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import java.time.Duration

class OpenllamaChatClient(private val baseUrl: String, private val model: String) : ChatClient {

    private val log = LoggerFactory.getLogger("ChatGPTClient");

    private val client = OkHttpClient()
        .newBuilder()
        .readTimeout(Duration.ofSeconds(60))
        .connectTimeout(Duration.ofSeconds(60))
        .build()

    override fun chat(prompt: String): String {
        val url = "$baseUrl/api/generate"

        val requestBody = """
            {
              "model": "$model",
              "prompt": "$prompt",
              "stream": false
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                log.error("Openllama chat client error")
                throw RuntimeException("Unexpected code $response")
            }

            return JsonPath.read(response.body?.string(), "$.response")
        }
    }
}
