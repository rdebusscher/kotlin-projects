package be.rubus.kotlin.microstream.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import one.microstream.persistence.types.Persister
import java.util.*

@Serializable
data class User(
    val name: String,
    var email: String,
) {

    val id: String = UUID.randomUUID().toString()

    // Books lent out by user.
    @Transient  // kotlinx.serialization.Transient: Not converted to JSON
    private val _books: MutableList<Book> = mutableListOf()

    val books: List<Book>
        get() = _books

    fun addBook(book: Book, persister: Persister) {
        _books.add(book)
        // Since we don't like to expose the actual list of Books (through getBooks)  as that
        // means we could alter the list outside the root, we provide the StorageManager as
        // parameter.
        persister.store(_books)
    }

}