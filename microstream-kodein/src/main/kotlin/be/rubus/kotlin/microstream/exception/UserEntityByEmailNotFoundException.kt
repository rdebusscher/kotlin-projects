package be.rubus.kotlin.microstream.exception

import be.rubus.kotlin.microstream.exception.BusinessException
import be.rubus.kotlin.microstream.exception.EntityNotFoundException
import be.rubus.kotlin.microstream.exception.MessageCode

class UserEntityByEmailNotFoundException(email: String) :
    BusinessException(MessageCode.USER_NOT_FOUND, "The user with email address '$email' could not be found")
    , EntityNotFoundException