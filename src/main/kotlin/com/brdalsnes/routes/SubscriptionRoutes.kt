package com.brdalsnes.routes

import com.brdalsnes.services.SubscriptionService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.subscriptionRoutes() {
    val service = SubscriptionService()
    route("/subscription") {
        post {
            service.add(call.receive())
            call.respond(HttpStatusCode.Created)
        }

        delete("{id}") {
            service.delete(call.parameters["id"]!!)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}