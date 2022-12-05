package be.rubus.kotlin.microstream

import be.rubus.kotlin.microstream.model.DataStorage
import one.microstream.storage.embedded.types.EmbeddedStorage
import java.nio.file.Paths

fun main() {
    val root = DataStorage()
    EmbeddedStorage.start(root, Paths.get("data")).use { storageManager ->

        // print the root to show its loaded content (stored in the last execution).
        println(root)
    }
}
