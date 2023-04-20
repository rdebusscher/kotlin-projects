package be.rubus.kotlin.microstream.exception

import be.rubus.kotlin.microstream.exception.types.HasMissingParameterException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * Exception handler configured for by the statusPage handling to have
 * a centralised point of handling Exceptions.
 */
object ExceptionHandler {

    suspend fun handle(
        call: ApplicationCall,
        cause: Throwable,
        debugMode: Boolean  // In debug mode status 500 has more info in response. Do not use in production.
    ) {
        when (cause) {
            is EntityNotFoundException -> {
                call.respond(
                    HttpStatusCode.NotFound,
                    ExceptionResponse(cause.message ?: cause.toString(), HttpStatusCode.NotFound.value)
                )
            }

            is BusinessException -> {
                call.respond(
                    HttpStatusCode.PreconditionFailed,
                    ExceptionResponse(cause.message ?: cause.toString(), cause.messageCode.value)
                )
            }

            is HasMissingParameterException -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse(cause.message ?: cause.toString(), HttpStatusCode.BadRequest.value)
                )
            }

            else -> {
                if (debugMode) {
                    // Printout stacktrace on console
                    cause.stackTrace.forEach { println(it) }
                    call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
                } else {

                    call.respondText(text = "Internal Error", status = HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}