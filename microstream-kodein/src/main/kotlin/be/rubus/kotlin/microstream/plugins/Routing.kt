package be.rubus.kotlin.microstream.plugins

import be.rubus.kotlin.microstream.routes.bookRouting
import be.rubus.kotlin.microstream.routes.userBookRouting
import be.rubus.kotlin.microstream.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    // Install the routing plugin
    routing {
        // This is the configuration of the routing
        get("/") {
            call.respondText("Hello World!")
        }
        bookRouting()
        userRouting()
        userBookRouting()
    }
}
