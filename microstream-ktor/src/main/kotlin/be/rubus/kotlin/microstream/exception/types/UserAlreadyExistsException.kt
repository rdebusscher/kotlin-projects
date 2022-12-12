package be.rubus.kotlin.microstream.exception.types

import be.rubus.kotlin.microstream.exception.BusinessException
import be.rubus.kotlin.microstream.exception.MessageCode

class UserAlreadyExistsException(email: String) :
    BusinessException(MessageCode.USER_ALREADY_EXISTS, "User with email $email already exists.")