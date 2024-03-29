/**
 * Copyright 2022-2023 Rudy De Busscher (https://www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.kotlin.ktor

import be.rubus.kotlin.ktor.exception.types.HasMissingParameterException
import be.rubus.kotlin.ktor.exception.types.ParameterTypeException
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

/**
 * Helper class to extract values from the URL path. Centralised so that we reuse names across
 * URLs to improve consistency.
 */
object URLParameters {
    fun extractBookId(context: PipelineContext<Unit, ApplicationCall>): Long {
        // When no bookId path parameter is present, throw an exception which is handled generically..
        val bookIdValue = context.call.parameters["bookId"] ?: throw HasMissingParameterException("Missing 'bookId' in URL")
        return try {
            isPositive(bookIdValue.toLong(), "bookId")
        } catch (e: NumberFormatException) {
            throw ParameterTypeException("Invalid bookId value: $bookIdValue")
        }
    }

    private fun isPositive(value: Long, parameterName: String): Long {
        if (value <= 0) {
            throw ParameterTypeException("Invalid $parameterName value: $value")
        }
        return value
    }
}