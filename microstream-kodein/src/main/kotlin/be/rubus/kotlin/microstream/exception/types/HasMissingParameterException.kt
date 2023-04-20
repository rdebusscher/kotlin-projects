package be.rubus.kotlin.microstream.exception.types

/**
 * Exception thrown when a URL parameter is not present.
 */
class HasMissingParameterException(message: String) : RuntimeException(message)