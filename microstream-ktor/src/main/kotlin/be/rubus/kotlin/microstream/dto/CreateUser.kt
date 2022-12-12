package be.rubus.kotlin.microstream.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUser(val name: String, val email: String)