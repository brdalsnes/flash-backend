package com.brdalsnes

import io.ktor.server.netty.*
import com.brdalsnes.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    if(testing) DatabaseFactoryTest.init() else DatabaseFactory.init()
    configureRouting()
    configureSerialization()
    configureStatusPages()
}
