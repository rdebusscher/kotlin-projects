package be.rubus.kotlin.microstream

/**
 * So that we can use a class that takes a MicroStream Persister and some data as
 * a singleton from within the root. The singleton is performing the 'domain' specific changes.
 */
open class SingletonHolder<out T, in A, in B>(private val constructor: (A, B) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(persister: A, data : B): T =
        instance ?: synchronized(this) {
            instance ?: constructor(persister, data).also { instance = it }
        }
}