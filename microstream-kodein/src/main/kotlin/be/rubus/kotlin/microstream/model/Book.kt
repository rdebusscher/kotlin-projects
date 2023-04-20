package be.rubus.kotlin.microstream.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val isbn: String,
    val name: String,
    val author: String,
    val year: Int
)