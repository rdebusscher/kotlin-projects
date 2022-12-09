package be.rubus.kotlin.microstream

import mu.KotlinLogging
import one.microstream.persistence.types.Persister
import one.microstream.storage.types.StorageManager

// No val or var for this parameter as we need to define a property that is Transient.
class Root(storageManager: StorageManager) {
    @Transient
    private val logger = KotlinLogging.logger {}

    // Transient because MicroStream doesn't need to store it. Type Persister has a special meaning
    // for MicroStream -> 'injected' when the class is read from storage.
    // when constructed
    @Transient
    val persister: Persister

    // The data of our database in this example
    private val items_ = mutableListOf<String>()

    init {
        // init block -> you can consider this as part of the Constructor of this class
        logger.entry("init")
        this.persister = storageManager
        // We need to define this instance as root for MicroStream
        this.persister.setRoot(this)
        this.persister.storeRoot()
    }

    // Expose the list as immutable
    val items: List<String>
        get() = items_

    fun addItem(item: String) {
        this.items_.add(item)
        // Store the changes
        persister.store(this.items_)
    }

}