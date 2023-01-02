package be.rubus.kotlin.ktor.annotated.resources

import be.rubus.kotlin.ktor.annotated.exception.ExceptionResponse
import be.rubus.kotlin.ktor.annotated.model.Person
import be.rubus.kotlin.ktor.annotated.processor.GET
import be.rubus.kotlin.ktor.annotated.processor.POST
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
    suspend fun savePerson(call: ApplicationCall) {
        val person = call.receive<Person>()
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
