package be.rubus.kotlin.microstream

fun main() {
    try {
        val data = DB.root
        println("Database content: ${data.items.joinToString()}")

    } finally {
        DB.shutdown()  // Stopping MicroStream
    }
}