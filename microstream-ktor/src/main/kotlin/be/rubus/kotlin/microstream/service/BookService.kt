package be.rubus.kotlin.microstream.service

import be.rubus.kotlin.microstream.model.Book

object BookService : AbstractService() {

    fun getBookByISBN(isbn: String): Book? {
        return root.books.all
            .find { b -> b.isbn == isbn }
    }
}