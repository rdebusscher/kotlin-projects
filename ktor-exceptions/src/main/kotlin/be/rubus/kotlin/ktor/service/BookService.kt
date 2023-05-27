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
package be.rubus.kotlin.ktor.service

import be.rubus.kotlin.ktor.exception.types.BookEntityNotFoundException
import be.rubus.kotlin.ktor.exception.types.LogicXException
import be.rubus.kotlin.ktor.model.Book

/**
 * An example of a service for showcasing the different exceptions.
 */
object BookService {
    fun getBook(bookId: String): Book {
        if (bookId == "9780747532743") {
            return Book("9780747532743", "Harry Potter", "Rowling, J.K.", 2000)
        }
        throw BookEntityNotFoundException(bookId)
    }

    fun performLogic() {
        throw LogicXException()
    }

    fun unexpectedException() {
        throw NumberFormatException("Something we did not expected like . and , differences in parsing")
    }

}