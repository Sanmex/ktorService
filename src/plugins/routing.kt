package com.example.plugins

import com.example.routing.notesRoutes
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hola mundas¡¡¡")
        }

    }
    notesRoutes()
}