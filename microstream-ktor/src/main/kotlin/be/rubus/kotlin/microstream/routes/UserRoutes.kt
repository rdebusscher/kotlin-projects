package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.database.DB
import be.rubus.kotlin.microstream.dto.CreateUser
import be.rubus.kotlin.microstream.exception.ExceptionResponse
import be.rubus.kotlin.microstream.exception.types.MissingBodyPropertyException
import be.rubus.kotlin.microstream.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun Route.userRouting() {
    route("/user") {
        get {
            call.respond(DB.root.users.all)
        }
        get("/{userId?}") {
            // The userId is missing when we have URL ending in /
            val userId = URLParameters.extractUserId(this)

            call.respond(UserService.getById(userId))
        }
        get("/by/{email?}") {
            // The email is missing when we have URL ending in /
            val email = call.parameters["email"] ?: return@get call.respond(
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse("Missing email", HttpStatusCode.BadRequest.value)
                )
            )

            val user = UserService.findByEmail(email) ?: return@get call.respond(
                call.respond(
                    HttpStatusCode.NotFound,
                    ExceptionResponse("No User with email", HttpStatusCode.NotFound.value)
                )
            )
            call.respond(user)
        }

        post {
            val createUser = call.receive<CreateUser>()
            call.respond(UserService.add(createUser))
        }

        patch("/{userId?}") {
            val userId = URLParameters.extractUserId(this)
            val body = call.receive<String>()

            val jsonObject = Json.parseToJsonElement(body).jsonObject
            val email = jsonObject["email"]?.jsonPrimitive?.content ?: throw MissingBodyPropertyException("email")

            call.respond(UserService.updateEmail(userId, email))
        }
    }
}