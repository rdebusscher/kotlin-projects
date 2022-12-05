package be.rubus.kotlin.microstream

import be.rubus.kotlin.microstream.model.DataStorage
import be.rubus.kotlin.microstream.model.Product
import be.rubus.kotlin.microstream.model.ProductType
import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.time.XTime
import java.nio.file.Paths

fun main() {
    val root = DataStorage()
    EmbeddedStorage.start(root, Paths.get("data")).use { storageManager ->

        // We change a property of root, but doesn't store it! When is it saved?
        root.timeStamp = XTime.now()

        if (root.products.size == 4) {
            root.products.add(Product(5, "Pizza", ProductType.ENTERTAINMENT))

            // Store what is changed!!
            storageManager.store(root.products)
        }
    }
}
