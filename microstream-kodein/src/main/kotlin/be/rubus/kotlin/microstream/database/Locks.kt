package be.rubus.kotlin.microstream.database

import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.function.Supplier

// Singleton that handles concurrent access to our database.
// Using the ReentrantReadWriteLock
object Locks {
    fun <T> readAction(lock: ReentrantReadWriteLock, supplier: Supplier<T>): T {
        lock.readLock().lock()
        return try {
            supplier.get()
        } finally {
            lock.readLock().unlock()
        }
    }

    fun <T> writeAction(lock: ReentrantReadWriteLock, supplier: Supplier<T>): T {
        lock.writeLock().lock()
        return try {
            supplier.get()
        } finally {
            lock.writeLock().unlock()
        }
    }


    val USERS = ReentrantReadWriteLock()

}