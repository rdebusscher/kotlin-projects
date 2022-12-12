package be.rubus.kotlin.microstream.exception

/**
 * Error should have a code so that it can be looked up easily in the
 * documentation what went wrong.
 */
enum class MessageCode(val value: Int) {
    INVALID_JSON_PROPERTY(422),
    BOOK_NOT_FOUND(1001),
    ALREADY_CREATED(1002),
    USER_NOT_FOUND(1101),
    USER_ALREADY_EXISTS(1102),
}

