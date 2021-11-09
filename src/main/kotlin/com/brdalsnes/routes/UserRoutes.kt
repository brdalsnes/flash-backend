package com.brdalsnes.routes

import com.brdalsnes.services.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoutes() {
    val service = UserService()
    route("/user") {
        get {
            val users = service.getAll()
            call.respond(users)
        }

        get("{id}") {
            val user = service.get(call.parameters["id"]!!)
            call.respond(user)
        }

        get("{id}/subscriptions") {
            val subscriptions = service.getSubscriptions(call.parameters["id"]!!)
            call.respond(subscriptions)
        }

        get("{id}/decks") {
            val decks = service.getDecks(call.parameters["id"]!!)
            call.respond(decks)
        }

        post {
            service.add(call.receive())
            call.respond(HttpStatusCode.Created)
        }

        put("{id}") {
            service.update(call.parameters["id"]!!, call.receive())
            call.respond(HttpStatusCode.OK)
        }

        delete("{id}") {
            service.delete(call.parameters["id"]!!)
            call.respond(HttpStatusCode.NoContent)
        }
    }

}