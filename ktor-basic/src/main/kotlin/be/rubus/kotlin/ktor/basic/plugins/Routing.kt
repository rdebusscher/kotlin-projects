/**
 * Copyright 2022 Rudy De Busscher (https://www.atbash.be)
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
package be.rubus.kotlin.ktor.basic.plugins

import be.rubus.kotlin.ktor.basic.exception.ExceptionResponse
import be.rubus.kotlin.ktor.basic.routes.jsonRouting
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            // val uri = call.request.uri  complete uri of request
            call.respondText("Hello World!")
        }
        get("/{name?}") {
            val name = call.parameters["name"] ?: return@get call.respond(
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse("Missing name parameter", HttpStatusCode.BadRequest.value)
                )
            )
            val language = call.request.queryParameters["language"] ?: "en"
            call.respondText("Hello $name with language $language")
        }

        // Define routing in 'modules
        jsonRouting()
    }
}
