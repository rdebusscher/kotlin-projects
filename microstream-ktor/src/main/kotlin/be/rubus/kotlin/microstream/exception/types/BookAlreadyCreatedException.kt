package be.rubus.kotlin.microstream.exception.types

import be.rubus.kotlin.microstream.exception.BusinessException
import be.rubus.kotlin.microstream.exception.MessageCode

class BookAlreadyCreatedException(name: String) :
    BusinessException(MessageCode.ALREADY_CREATED, "The book with name '$name' already exists.")