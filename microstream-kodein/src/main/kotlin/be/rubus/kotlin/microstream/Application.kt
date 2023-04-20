package be.rubus.kotlin.microstream

import be.rubus.kotlin.microstream.di.configureDI
import be.rubus.kotlin.microstream.plugins.configureExceptions
import be.rubus.kotlin.microstream.plugins.configureRouting
import be.rubus.kotlin.microstream.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Extension function on Application that delegates to another extension functions
    configureSerialization()
    configureRouting()
    configureExceptions()
    configureDI()
}
