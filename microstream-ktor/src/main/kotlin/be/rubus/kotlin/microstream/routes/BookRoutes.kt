package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.database.DB
import be.rubus.kotlin.microstream.exception.ExceptionResponse
import be.rubus.kotlin.microstream.exception.types.BookEntityNotFoundException
import be.rubus.kotlin.microstream.service.BookService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bookRouting() {
    route("/book") {
        get {
            call.respond(DB.root.books.all)
        }

        get("{isbn?}") {
            // The isbn is missing when we have URL ending in /
            val isbn = call.parameters["isbn"] ?: return@get call.respond(
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse("Missing isbn", HttpStatusCode.BadRequest.value)
                )
            )
            // Find book by ISBN
            val book = BookService.getBookByISBN(isbn) ?: throw BookEntityNotFoundException(isbn)
            call.respond(book)
        }
    }
}