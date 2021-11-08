package com.brdalsnes.routes

import com.brdalsnes.services.CardService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.cardRoutes() {
    val service = CardService()
    route("/card") {
        get("{id}") {
            val card = service.get(call.parameters["id"]!!)
            call.respond(card)
        }

        post {
            service.add(call.receive())
            call.respond(HttpStatusCode.Created)
        }

        patch("{id}") {
            service.update(call.parameters["id"]!!, call.receive())
            call.respond(HttpStatusCode.OK)
        }

        delete("{id}") {
            service.delete(call.parameters["id"]!!)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}