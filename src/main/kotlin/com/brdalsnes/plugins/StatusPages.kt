package com.brdalsnes.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.lang.IllegalArgumentException

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<IllegalArgumentException> {
            call.respond(HttpStatusCode.BadRequest)
        }
        exception<ExposedSQLException> {
            call.respond(HttpStatusCode.Conflict)
        }
        exception<NotFoundException> { cause ->
            call.respondText(cause.message ?: "Not found", status = HttpStatusCode.NotFound)
        }
    }
}