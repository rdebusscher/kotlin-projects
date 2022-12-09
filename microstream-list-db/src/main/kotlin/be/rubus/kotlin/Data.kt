package be.rubus.kotlin

class Data {
    // A mutable list that is kept internal
    private val items_ = mutableListOf<String>()

    // Expose a read-only version
    val items: List<String>
        get() = items_

    fun addItem(item: String) {
        items_.add(item)
    }

    fun removeItem(item: String) {
        items_.remove(item)
    }
}