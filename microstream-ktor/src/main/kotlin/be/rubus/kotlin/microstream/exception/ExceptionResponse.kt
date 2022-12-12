package be.rubus.kotlin.microstream.exception

import kotlinx.serialization.Serializable

/**
 * Object that is used in responses to have JSON formatted error messages.
 */
@Serializable
data class ExceptionResponse(
    val message: String,
    val code: Int
)