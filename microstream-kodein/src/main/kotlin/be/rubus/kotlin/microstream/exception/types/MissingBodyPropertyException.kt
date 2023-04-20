package be.rubus.kotlin.microstream.exception.types

import be.rubus.kotlin.microstream.exception.BusinessException
import be.rubus.kotlin.microstream.exception.MessageCode

/**
 * Exception when the JSON within body has not the required property.
 */
class MissingBodyPropertyException(requiredPropertyName: String) :
    BusinessException(
        MessageCode.INVALID_JSON_PROPERTY,
        "The request body is missing the JSON property $requiredPropertyName"
    )