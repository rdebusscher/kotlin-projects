package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.exception.ExceptionResponse
import be.rubus.kotlin.microstream.service.UserBookService
import be.rubus.kotlin.microstream.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.userBookRouting() {
    val userService: UserService by closestDI().instance()
    val userBookService: UserBookService by closestDI().instance()

    route("/user/{userId}") {
        get("/book") {
            val userId = URLParameters.extractUserId(this)

            call.respond(userService.getById(userId).books)
        }
        post("/book/{bookId?}") {
            val userId = URLParameters.extractUserId(this)
            val bookId = URLParameters.extractBookId(this)
            userBookService.addBookToUser(userId, bookId)
            call.respond(HttpStatusCode.Created)
        }
    }
}
