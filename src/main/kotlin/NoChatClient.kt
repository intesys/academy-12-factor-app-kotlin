package org.example

class NoChatClient: ChatClient {
    override fun chat(prompt: String): String {
        Thread.sleep(2_000)
        return "Chat client is disabled"
    }
}
