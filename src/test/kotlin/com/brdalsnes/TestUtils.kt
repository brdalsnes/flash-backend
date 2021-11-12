package com.brdalsnes

import io.ktor.server.testing.*

fun withTestServer( block: TestApplicationEngine.() -> Unit) {
    withTestApplication({ module(testing = true) }, block)
}