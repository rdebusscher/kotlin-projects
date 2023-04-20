package be.rubus.kotlin.microstream

/**
 * So that we can use a class that takes some data as
 * a singleton from within the root. The singleton is performing the 'domain' specific changes.
 */
open class SingletonHolder<out T, in D>(private val constructor: (D) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(data: D): T =
        instance ?: synchronized(this) {
            instance ?: constructor(data).also { instance = it }
        }
}