package be.rubus.kotlin.microstream.model

import one.microstream.time.XTime
import java.util.*

data class DataStorage(var timeStamp : Date = XTime.now(), val products : MutableList<Product> = mutableListOf())