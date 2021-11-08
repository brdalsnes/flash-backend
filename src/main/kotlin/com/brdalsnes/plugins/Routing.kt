package com.brdalsnes.plugins

import com.brdalsnes.routes.cardRoutes
import com.brdalsnes.routes.deckRoutes
import com.brdalsnes.routes.userRoutes
import io.ktor.routing.*
import io.ktor.application.*

fun Application.configureRouting() {
    routing {
        userRoutes()
        deckRoutes()
        cardRoutes()
    }
}
