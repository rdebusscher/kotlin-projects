package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.dto.CreateUser
import be.rubus.kotlin.microstream.exception.ExceptionResponse
import be.rubus.kotlin.microstream.exception.UserEntityByEmailNotFoundException
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
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.userRouting() {
    val userService: UserService by closestDI().instance()

    route("/user") {
        get {
            call.respond(userService.all())
        }

        get("/{userId?}") {
            // The userId is missing when we have URL ending in /
            val userId = URLParameters.extractUserId(this)

            call.respond(userService.getById(userId))
        }
        get("/by/{email?}") {
            // The email is missing when we have URL ending in /
            val email = URLParameters.extractEmail(this)
            val user = userService.findByEmail(email) ?: throw UserEntityByEmailNotFoundException(email)
            call.respond(user)
        }

        post {
            val createUser = call.receive<CreateUser>()
            call.respond(userService.add(createUser))
        }

        patch("/{userId?}") {
            val userId = URLParameters.extractUserId(this)
            val body = call.receive<String>()

            val jsonObject = Json.parseToJsonElement(body).jsonObject
            val email = jsonObject["email"]?.jsonPrimitive?.content ?: throw MissingBodyPropertyException("email")

            call.respond(userService.updateEmail(userId, email))
        }
    }
}