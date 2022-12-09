package be.rubus.kotlin.microstream

fun main() {
    try {
        val data = DB.root
        if (data.items.isEmpty()) {
            data.addItem("Java")
            data.addItem("Kotlin")
            println("Data items are added")
        }
    } finally {
        DB.shutdown()  // Stopping MicroStream

    }
}