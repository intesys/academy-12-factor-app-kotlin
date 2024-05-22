package org.example

import io.javalin.Javalin

fun main() {
    val appConfigs = Configs()

    val app = Javalin.create(/*config*/)
        .get("/") { ctx -> ctx.result(appConfigs.getCustomMessage()) }
        .start(appConfigs.getServerPort())
}
