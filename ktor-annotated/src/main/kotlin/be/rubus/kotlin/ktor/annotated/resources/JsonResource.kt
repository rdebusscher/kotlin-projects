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
package be.rubus.kotlin.ktor.annotated.resources

import be.rubus.kotlin.ktor.annotated.exception.ExceptionResponse
import be.rubus.kotlin.ktor.annotated.model.Person
import be.rubus.kotlin.ktor.annotated.processor.GET
import be.rubus.kotlin.ktor.annotated.processor.POST
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class JsonResource {
    @GET("/person")
    suspend fun getPerson(call: ApplicationCall) {
        call.respond(Person("rudy", 42))
    }

    @GET("/person/jsonp")
    suspend fun getJsonP(call: ApplicationCall) {
        val result = buildJsonObject {
            put("type", "json")
            put("age", 42)
            put("name", "Rudy")
        }
        call.respond(result)
    }

    @POST("/person")
    suspend fun savePerson(call: ApplicationCall, person: Person) {
        // The body can be retrieved by defining a function parameter. Conversion from JSON happens automatically.
        call.respondText("POST received with name ${person.name} and age ${person.age}")

    }

    @GET("/person/{id?}")
    suspend fun getPersonById(call: ApplicationCall, id: String) {
        id.toIntOrNull() ?: return call.respond(
            HttpStatusCode.BadRequest,
            ExceptionResponse("Invalid id, must be a integer", HttpStatusCode.BadRequest.value)
        )

        call.respond(status = HttpStatusCode.Accepted, mapOf("result" to id))
    }

}
