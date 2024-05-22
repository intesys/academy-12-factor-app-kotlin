package org.example

class Configs {

    /**
     * Test environment
     */
    val CUSTOM_MESSAGE_TEST: String = "Hello World Test!"
    val PORT_TEST: Int = 8080
    val API_KEY_TEST: String = "abc1234"

    /**
     * Prod environment
     */
    val CUSTOM_MESSAGE_PROD: String = "Hello World Prod!"
    val PORT_PROD: Int = 9090
    val API_KEY_PROD: String = "abc1234"

    fun getCustomMessage(): String {
        val env = System.getenv("environment")
        return if (env == "prod") {
            CUSTOM_MESSAGE_PROD
        } else {
            println("No environment test, fallback to test environments")
            CUSTOM_MESSAGE_TEST
        }
    }

    fun getServerPort(): Int {
        val env = System.getenv("environment")
        return if (env == "prod") {
            PORT_PROD
        } else {
            PORT_TEST
        }
    }
}

