package be.rubus.kotlin.microstream.database

import be.rubus.kotlin.microstream.model.Book
import be.rubus.kotlin.microstream.model.Books
import be.rubus.kotlin.microstream.model.User
import be.rubus.kotlin.microstream.model.Users
import one.microstream.persistence.types.Persister
import one.microstream.storage.types.StorageManager

class Root(storageManager: StorageManager) {
    // Transient as not needed to be stored. And using type Persister as
    // StorageManager will be injected when loading from the external storage.
    @Transient
    val persister: Persister

    // The collections that make up our database
    private val _users = mutableListOf<User>()
    private val _books = mutableListOf<Book>()

    // This init must come after the declaration of properties, otherwise they are not initialized
    init {
        persister = storageManager
        storageManager.setRoot(this)
        storageManager.storeRoot()
    }

    // To expose different domain handling objects
    // This is actually only a getter
    val users: Users
        get() = Users.getInstance(persister, _users)

    val books: Books
        get() = Books.getInstance(persister, _books)

}