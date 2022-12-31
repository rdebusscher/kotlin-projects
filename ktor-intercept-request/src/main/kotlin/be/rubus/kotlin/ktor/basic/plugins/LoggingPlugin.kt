/**
 * Copyright 2022 Rudy De Busscher (https://www.atbash.be)
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
package be.rubus.kotlin.ktor.basic.plugins

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.util.*
import java.util.*

val timestampAttribute = AttributeKey<Date>("timestamp")

val LoggingPlugin = createApplicationPlugin(name = "LoggingPlugin") {
    println("LoggingPlugin is installed!")

    on(CallSetup) { call ->
        call.attributes.put(timestampAttribute, Date())
    }

    on(ResponseBodyReadyForSend) { call, _ ->
        val start = call.attributes[timestampAttribute]
        call.response.headers.append("X-timing", timeDifference(start, Date()))
    }

}

fun timeDifference(start: Date, end: Date): String {
    val diff = end.time - start.time
    val seconds = diff / 1000 % 60
    val ms = diff - seconds * 1000
    return "Call took $seconds seconds and $ms milliseconds"
}