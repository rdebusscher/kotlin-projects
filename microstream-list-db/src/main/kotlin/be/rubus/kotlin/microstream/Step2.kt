package be.rubus.kotlin.microstream

fun main() {
    try {
        val data = DB.root
        if (!data.items.contains("MicroStream")) {
            data.addItem("MicroStream")
            println("MicroStream item added")
        }
    } finally {
        DB.shutdown()  // Stopping MicroStream

    }
}