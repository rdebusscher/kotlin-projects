package be.rubus.kotlin.microstream.model

import be.rubus.kotlin.microstream.SingletonHolder
import one.microstream.persistence.types.Persister

class Books(
    // No need to store in data Storage by MicroStream
    @Transient private val persister: Persister,

    // No need to store in storage, already stored by root
    @Transient private val books_: MutableList<Book>
) {

    // So that we can expose Books as singleton.
    companion object : SingletonHolder<Books, Persister, MutableList<Book>>(::Books)

    // expose as read only list
    val all: List<Book>
        get() = books_

    fun add(book: Book) {
        books_.add(book)
        persister.store(books_)
    }
}