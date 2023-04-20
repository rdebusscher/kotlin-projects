package be.rubus.kotlin.microstream.database

import be.rubus.kotlin.microstream.model.Book
import be.rubus.kotlin.microstream.model.Books
import be.rubus.kotlin.microstream.model.User
import be.rubus.kotlin.microstream.model.Users

class Root {

    // The collections that make up our database
    private val _users = mutableListOf<User>()
    private val _books = mutableListOf<Book>()


    // To expose different domain handling objects
    // This is actually only a getter
    val users: Users
        get() = Users.getInstance(_users)

    val books: Books
        get() = Books.getInstance(_books)
}