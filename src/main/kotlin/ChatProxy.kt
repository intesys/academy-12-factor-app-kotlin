package org.example

import org.slf4j.LoggerFactory

class ChatProxy: ChatClient {
    val log = LoggerFactory.getLogger("main")

    val config = Configs();

    override fun chat(prompt: String): String {
        return getChatClient().chat(prompt)
    }

    private fun getChatClient(): ChatClient {
        val chatClientToUse = config.getProperty("CHAT_CLIENT");

        when (chatClientToUse) {
            "OPENLLAMA" -> {
                log.info("instantiating openllama")
                return OpenllamaChatClient(config.getProperty("OPENLLAMA_URL"), config.getProperty("MODEL"))
            }
            "CHATGPT" -> {
                log.info("instantiating chatgpt")
                return ChatGPTChatClient(config.getProperty("CHATGPT_API_KEY"), config.getProperty("MODEL"))
            }
            else -> {
                log.warn("WARN no chat configured")
                return NoChatClient()
            }
        }
    }
}
