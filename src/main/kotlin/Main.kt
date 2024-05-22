package org.example

import io.javalin.Javalin

fun main() {
    val appConfigs = Configs()

    val app = Javalin.create(/*config*/)
        .get("/") { ctx -> ctx.result(appConfigs.getProperty("CUSTOM_MESSAGE")) }
        .start(appConfigs.getIntegerProperty("PORT"))
}
