package be.rubus.kotlin.microstream

import mu.KotlinLogging
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration
import one.microstream.storage.types.StorageManager

object DB {
    private val logger = KotlinLogging.logger {}

    private val storageManager: StorageManager by lazy {
        // Only initialised when accessed, not at program start
        logger.entry("storageManager Lazy")
        startAndInitialiseDatabase()
    }

    private fun startAndInitialiseDatabase(): StorageManager {
        logger.entry("startAndInitialiseDatabase")
        val result = createStorageManager()
        val root = result.root() as Root?
        initRoot(result, root)
        return result
    }

    private fun createStorageManager(): StorageManager {
        logger.entry("createStorageManager")
        // requires  microstream-storage-embedded-configuration dependency
        return EmbeddedStorageConfiguration.Builder()
            .setStorageDirectory("microstream-data")
            .createEmbeddedStorageFoundation()

            .createEmbeddedStorageManager()
            .start()
    }

    private fun initRoot(storageManager: StorageManager, root: Root?) {
        logger.entry("initRoot")
        if (root == null) {
            // This sets also the Root instance as Root object within StorageManager
            Root(storageManager)
        }
    }

    fun shutdown() {
        //Otherwise our application doesn't stop'
        storageManager.shutdown()
    }

    // expose the root object
    val root: Root
        get() = storageManager.root() as Root
}