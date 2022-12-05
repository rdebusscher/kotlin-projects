package be.rubus.kotlin.microstream

import be.rubus.kotlin.microstream.model.DataStorage
import be.rubus.kotlin.microstream.model.ProductType
import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.time.XTime
import java.nio.file.Paths

fun main() {
    val root = DataStorage()
    EmbeddedStorage.start(root, Paths.get("data")).use { storageManager ->

        // We change a property of root, but doesn't store it! When is it saved?
        root.timeStamp = XTime.now()

        if (root.products.size == 5) {
            val productItem = root.products.asSequence().filter { it.id == 5L }

            if (productItem.any()) {
                val product = productItem.first()
                product.productType = ProductType.FOOD

                // Store what is changed!!
                storageManager.store(product)
            } else {
                println("Oops, product not found")
            }


        }
    }
}
