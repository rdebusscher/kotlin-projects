package be.rubus.kotlin.microstream.model

import be.rubus.kotlin.microstream.SingletonHolder
import one.microstream.experimental.integration.kodein.ContainerAware
import one.microstream.persistence.types.Persister
import org.kodein.di.instance

class Books(
    // No need to store in storage, already stored by root
    @Transient private val books_: MutableList<Book>
) : ContainerAware() {

    private val persister: Persister by di.instance()

    // So that we can expose Books as singleton.
    companion object : SingletonHolder<Books, MutableList<Book>>(::Books)

    // expose as read only list
    val all: List<Book>
        get() = books_

    fun add(book: Book) {
        books_.add(book)
        persister.store(books_)
    }
}