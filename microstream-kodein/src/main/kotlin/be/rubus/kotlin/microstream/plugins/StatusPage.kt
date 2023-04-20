package be.rubus.kotlin.microstream.plugins

import be.rubus.kotlin.microstream.exception.ExceptionHandler
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureExceptions() {
    val debugMode = checkDebugMode()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            ExceptionHandler.handle(call, cause, debugMode)
        }
    }
}

const val DEBUG_PARAMETER = "app.debug"

fun checkDebugMode(): Boolean {
    val value = System.getenv(DEBUG_PARAMETER) ?: System.getProperty(DEBUG_PARAMETER, "false")
    return value.toBoolean()
}