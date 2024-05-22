package org.example

class ChatProxy: ChatClient {
    val config = Configs();

    override fun chat(prompt: String): String {
        return getChatClient().chat(prompt)
    }

    private fun getChatClient(): ChatClient {
        val chatClientToUse = config.getProperty("CHAT_CLIENT");

        when (chatClientToUse) {
            "OPENLLAMA" -> {
                println("instantiating openllama")
                return OpenllamaChatClient(config.getProperty("OPENLLAMA_URL"), config.getProperty("MODEL"))
            }
            "CHATGPT" -> {
                println("instantiating chatgpt")
                return ChatGPTChatClient(config.getProperty("CHATGPT_API_KEY"), config.getProperty("MODEL"))
            }
            else -> {
                println("WARN no chat configured")
                return NoChatClient()
            }
        }
    }
}
