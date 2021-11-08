package com.brdalsnes.routes

import com.brdalsnes.services.DeckService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.deckRoutes() {
    val service = DeckService()
    route("/deck") {
        get {
            val decks = service.getAll()
            call.respond(decks)
        }

        get("{id}") {
            val deck = service.get(call.parameters["id"]!!)
            call.respond(deck)
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