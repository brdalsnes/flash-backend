package com.brdalsnes

import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

fun withTestServer( block: TestApplicationEngine.() -> Unit) {
    withTestApplication({ module(testing = true) }, block)
}

inline fun <reified  T> getModelFromResponse(response: TestApplicationResponse): T {
    return Json.decodeFromJsonElement(Json.parseToJsonElement(response.content ?: ""))
}