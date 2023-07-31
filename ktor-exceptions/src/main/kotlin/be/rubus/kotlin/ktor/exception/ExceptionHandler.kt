/**
 * Copyright 2022-2023 Rudy De Busscher (https://www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.kotlin.ktor.exception

import be.rubus.kotlin.ktor.exception.types.ParameterException
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
        developmentMode: Boolean  // In development mode status, 500 has more info in response. Do not use in production.
    ) {
        when (cause) {
            is EntityNotFoundException -> {
                // for the cases the user specified an URL parameter where the id doesn't exist.
                call.respond(
                    HttpStatusCode.NotFound,
                    ExceptionResponse(cause.message ?: cause.toString(), HttpStatusCode.NotFound.value)
                )
            }

            is BusinessException -> {
                // Some business logic error, status 412 Precondition Failed is appropriate here
                call.respond(
                    HttpStatusCode.PreconditionFailed,
                    ExceptionResponse(cause.message ?: cause.toString(), cause.messageCode.value)
                )
            }

            is ParameterException -> {
                // The client forgot to define a Path or Query parameter or used a wrong type (string and not a number)
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse(cause.message ?: cause.toString(), HttpStatusCode.BadRequest.value)
                )
            }
            // We can have other categories
            else -> {
                // All the other Exceptions become status 500, with more info in development mode.
                if (developmentMode) {
                    // Printout stacktrace on console
                    cause.stackTrace.forEach { println(it) }
                    call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
                } else {
                    // We are in production, so only minimal info.
                    call.respondText(text = "Internal Error", status = HttpStatusCode.InternalServerError)
                }
            }


        }
    }
}