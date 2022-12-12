package be.rubus.kotlin.microstream.exception

/**
 * Top level exception for all Business exceptions (problem during business validation)
 * Any exception extending this type has special handling by the {@see ExceptionHandler}
 */
open class BusinessException(val messageCode: MessageCode, message: String?) : RuntimeException(message)