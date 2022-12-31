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
package be.rubus.kotlin.ktor.basic

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.*

suspend fun authInterceptor(call: ApplicationCall): Boolean {
    val authHeader = call.request.headers["Authorization"]
    return if (authHeader != null) {
        val parts = authHeader.split(" ")
        if (parts[0] =="xxx") {
            val userName = parseUserNameFromToken(parts[1])
            call.attributes.put(AttributeKey("userName"), userName)
            true
        } else {
            // We expect the type of authorization to be xxx
            unauthorized(call)
            false
        }
    } else {
        // No header.
        unauthorized(call)
        false
    }
}

private suspend fun unauthorized(call: ApplicationCall) {
    call.respond(HttpStatusCode.Unauthorized)
}

fun parseUserNameFromToken(token: String): String {
    // In this example, I assume the header just contains the name
    return token
}
