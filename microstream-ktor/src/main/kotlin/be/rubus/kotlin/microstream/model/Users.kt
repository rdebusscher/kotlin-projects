package be.rubus.kotlin.microstream.model

import be.rubus.kotlin.microstream.SingletonHolder
import one.microstream.persistence.types.Persister

/**
 * Handles the updates on User objects. exposes the User list
 */
class Users(
    // No need to store in data Storage by MicroStream
    @Transient private val persister: Persister,

    // No need to store in storage, already stored by root
    @Transient private val users_: MutableList<User>
) {

    // So that we can expose Books as singleton.
    companion object : SingletonHolder<Users, Persister, MutableList<User>>(::Users)

    // expose users as read only list
    val all: List<User>
        get() = users_

    fun addUser(user: User): User {
        users_.add(user)
        persister.store(users_)
        return user
    }

    /**
     * Since the User instance is already part of the User Collection, we just need
     * to make it is stored externally.
     *
     * @param user
     */
    fun updateUser(user: User) {
        persister.store(user)
    }

    fun removeUser(user: User) {
        users_.remove(user)
        persister.store(users_)
    }

    fun addBookToUser(user: User, book: Book) {
        user.addBook(book, persister)
    }
}