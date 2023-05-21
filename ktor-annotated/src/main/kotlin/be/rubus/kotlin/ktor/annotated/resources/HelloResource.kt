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
package be.rubus.kotlin.ktor.annotated.resources

import be.rubus.kotlin.ktor.annotated.processor.DefaultValue
import be.rubus.kotlin.ktor.annotated.processor.GET
import io.ktor.server.application.*
import io.ktor.server.response.*

class HelloResource {

    @GET("/")
    suspend fun helloWorld(call: ApplicationCall) {
        // val uri = call.request.uri  complete uri of request
        call.respondText("Hello World!")
    }

    @GET("/{name}")
    suspend fun greeting(call: ApplicationCall, name: String, @DefaultValue("en") language: String?) {
        // function parameter names must match either variable name in URL pattern or is matched against query parameters
        call.respondText("Hello $name with language $language")
    }

}