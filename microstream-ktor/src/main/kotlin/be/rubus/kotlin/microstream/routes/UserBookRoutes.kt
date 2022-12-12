package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.exception.ExceptionResponse
import be.rubus.kotlin.microstream.service.UserBookService
import be.rubus.kotlin.microstream.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userBookRouting() {
    route("/user/{userId}") {
        get("/book") {
            val userId = URLParameters.extractUserId(this)

            call.respond(UserService.getById(userId).books)
        }
        post("/book/{bookId?}") {
            val userId = URLParameters.extractUserId(this)
            val bookId = call.parameters["bookId"] ?: return@post call.respond(
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse("Missing book id", HttpStatusCode.BadRequest.value)
                )
            )
            UserBookService.addBookToUser(userId, bookId)
        }
    }
}
