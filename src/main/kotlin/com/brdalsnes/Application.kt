package com.brdalsnes

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.brdalsnes.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        DatabaseFactory.init()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
