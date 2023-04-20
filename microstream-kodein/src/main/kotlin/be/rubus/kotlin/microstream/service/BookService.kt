package be.rubus.kotlin.microstream.service

import be.rubus.kotlin.microstream.model.Book

class BookService : AbstractService() {

    fun all(): List<Book> {
        return root.books.all
    }

    fun getBookByISBN(isbn: String): Book? {
        return root.books.all
            .find { b -> b.isbn == isbn }
    }
}