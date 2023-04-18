package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.service.UserBookService
import be.rubus.kotlin.microstream.service.UserService
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
            val bookId = URLParameters.extractBookId(this)
            UserBookService.addBookToUser(userId, bookId)
        }
    }
}
