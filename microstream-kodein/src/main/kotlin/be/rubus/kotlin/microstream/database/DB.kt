package be.rubus.kotlin.microstream.database

import one.microstream.experimental.integration.kotlin.StorageManagerBuilder
import java.nio.file.Paths

/**
 *
 */
object DB {
    // This is provider version, the actual instance can be accessed through storageManager.value
    val storageManager = lazy {
        StorageManagerBuilder()
            .withFileStorageSystem()
                .withPath(Paths.get("microstream-data"))
                .endSystemConfig()
            .buildAndStart()
    }

    // If you want to start StorageManager at start of the application, you can use
    // val storageManager by lazy {
    // which starts the StorageManager when the Kodein config is processed or
    // val storageManager =
    // which starts it immediately when variable is created.
}