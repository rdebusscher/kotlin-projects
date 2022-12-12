package be.rubus.kotlin.microstream.exception.types

import be.rubus.kotlin.microstream.exception.BusinessException
import be.rubus.kotlin.microstream.exception.EntityNotFoundException
import be.rubus.kotlin.microstream.exception.MessageCode

class BookEntityNotFoundException(isbn: String) :
    BusinessException(MessageCode.BOOK_NOT_FOUND, "The book with ISBN $isbn could not be found.")
    , EntityNotFoundException