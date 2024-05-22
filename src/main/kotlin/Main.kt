package org.example

import com.jayway.jsonpath.JsonPath
import io.javalin.Javalin
import org.slf4j.LoggerFactory

fun main() {

    val config = Configs();

    val app = Javalin.create { config ->
        config.staticFiles.add("/public")
    }.start(config.getIntegerProperty("PORT"))

    app.get("/configs") { ctx ->
        ctx.json(mapOf("ai" to config.getProperty("CHAT_CLIENT")))
    }

    app.post("/chat") { ctx ->
        val prompt = JsonPath.read<String>(ctx.body(), "$.prompt")
        println("Querying chat proxy with promt '$prompt'")
        val chat = ChatProxy()
        val resultString = prompt?.let { chat.chat(prompt) }
        println("Received results from chat client")
        if (resultString != null) {
            ctx.json(mapOf("result" to resultString))
        } else {
            ctx.json(mapOf("result" to "no result"))
        }
    }

}
