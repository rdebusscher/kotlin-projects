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
package be.rubus.kotlin.ktor.exception

/**
 * Error should have a code so that it can be looked up easily in the
 * documentation what went wrong. We can define them all here to keep thm unique.
 */
enum class MessageCode(val value: Int) {
    BOOK_NOT_FOUND(1001),
    BUSINESS_LOGIC_ERROR_X(1002)
}

