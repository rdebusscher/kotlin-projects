package be.rubus.kotlin.microstream.service

import be.rubus.kotlin.microstream.exception.types.BookAlreadyCreatedException
import be.rubus.kotlin.microstream.exception.types.BookEntityNotFoundException
import org.kodein.di.instance

class UserBookService : AbstractService() {
    private val userService: UserService by di.instance()
    private val bookService: BookService by di.instance()

    fun addBookToUser(id: String, isbn: String) {
        // This is not as safe as using Locks.  We don't protect reading
        // the books of the user when we are in this method.
        synchronized(USER_BOOK_LOCK) {
            val byId = userService.getById(id)

            val bookByISBN = bookService.getBookByISBN(isbn) ?: throw BookEntityNotFoundException(isbn)

            if (byId.books.contains(bookByISBN)) {
                throw BookAlreadyCreatedException(bookByISBN.name)
            }
            root.users.addBookToUser(byId, bookByISBN)
        }
    }


    private val USER_BOOK_LOCK = Any()

}