package be.rubus.kotlin

fun main() {
    val data = Data()
    // No data.items.add -> external list is immutable
    data.addItem("Java")
    data.addItem("Kotlin")
    data.addItem("MicroStream")

    println(data.items.joinToString())


    data.removeItem("Java")
    println(data.items.joinToString())
}