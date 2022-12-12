package be.rubus.kotlin.microstream.exception.types

import be.rubus.kotlin.microstream.exception.BusinessException
import be.rubus.kotlin.microstream.exception.EntityNotFoundException
import be.rubus.kotlin.microstream.exception.MessageCode

class UserEntityNotFoundException(userId: String) :
    BusinessException(MessageCode.USER_NOT_FOUND, "The user with id '$userId' could not be found")
    , EntityNotFoundException