package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.exception.ExceptionResponse
import be.rubus.kotlin.microstream.exception.types.BookEntityNotFoundException
import be.rubus.kotlin.microstream.service.BookService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.bookRouting() {
    val bookService: BookService by closestDI().instance()

    route("/book") {
        get {
            call.respond(bookService.all())
        }

        get("{isbn?}") {
            // The isbn is missing when we have URL ending in /
            val isbn = URLParameters.extractIsbn(this)
            // Find book by ISBN
            val book = bookService.getBookByISBN(isbn) ?: throw BookEntityNotFoundException(isbn)
            call.respond(book)
        }
    }
}