package be.rubus.kotlin.microstream.service

import be.rubus.kotlin.microstream.database.Locks
import be.rubus.kotlin.microstream.dto.CreateUser
import be.rubus.kotlin.microstream.exception.types.UserAlreadyExistsException
import be.rubus.kotlin.microstream.exception.types.UserEntityNotFoundException
import be.rubus.kotlin.microstream.model.User
import java.util.*

object UserService : AbstractService() {

    fun getById(id: String): User {
        return locks.readAction(
            Locks.USERS
        ) {
            val result : User? = root.users.all
                .find { u -> u.id == id }
            result ?: throw UserEntityNotFoundException(id)
        }
    }

    fun findByEmail(email: String): User? {
        return locks.readAction(
            Locks.USERS
        ) {
            root.users.all
                .find { u -> email == u.email }
        }
    }

    fun add(user: CreateUser): User {
        // This block also protects that multiple threads modify the User collection
        // at the time MicroStream stores the changes (avoids ConcurrentModificationException)
        return locks.writeAction(
            Locks.USERS
        ) {
            val byEmail = findByEmail(user.email)
            if (byEmail != null) {
                throw UserAlreadyExistsException(user.email)
            }
            root.users.addUser(User(user.name, user.email))
        }
    }

    fun updateEmail(id: String, email: String): User {
        return locks.writeAction(
            Locks.USERS
        ) {
            val byId = getById(id)

            byId.email = email
            root.users.updateUser(byId)
            byId// return updated version
        }
    }

    fun removeById(id: String?) {
        locks.writeAction(
            Locks.USERS
        ) {
            val userById: Optional<User> = root.users.all.stream()
                .filter { u -> u.id == id }
                .findAny()
            userById.ifPresent(root.users::removeUser)
            null
        }
    }
}