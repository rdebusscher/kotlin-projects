package be.rubus.kotlin.microstream

import be.rubus.kotlin.microstream.model.DataStorage
import be.rubus.kotlin.microstream.model.Product
import be.rubus.kotlin.microstream.model.ProductType
import one.microstream.storage.embedded.types.EmbeddedStorage
import java.nio.file.Paths

fun main() {
    val root = DataStorage()
    EmbeddedStorage.start(root, Paths.get("data")).use { storageManager ->

        if (root.products.isEmpty()) {
            root.products.add(Product(1, "Bread", ProductType.FOOD))
            root.products.add(Product(2, "Orange Juice", ProductType.FOOD))
            root.products.add(Product(3, "Floor cleaning", ProductType.CLEANING))
            root.products.add(Product(4, "Cinema Tickets", ProductType.ENTERTAINMENT))

            storageManager.store(root.products)
        }
    }
}
