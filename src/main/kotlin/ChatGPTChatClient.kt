package org.example

import com.jayway.jsonpath.JsonPath
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ChatGPTChatClient(private val apiKey: String, private val model: String) : ChatClient {

    private val client = OkHttpClient()

    override fun chat(prompt: String) : String {
        val url = "https://api.openai.com/v1/chat/completions"

        val requestBody = """
            {
              "model": "$model",
              "messages": [
                {
                  "role": "system",
                  "content": "You are a helpful assistant."
                },
                {
                  "role": "user",
                  "content": "$prompt"
                }
              ]
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("Unexpected response")
                throw RuntimeException("Unexpected code $response")
            }
            return JsonPath.read(response.body?.string(), "$.choices[0].message.content")
        }
    }
}
