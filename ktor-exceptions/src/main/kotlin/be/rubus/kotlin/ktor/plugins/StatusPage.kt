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
package be.rubus.kotlin.ktor.plugins

import be.rubus.kotlin.ktor.exception.ExceptionHandler
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureExceptions() {
    val developmentMode = isDevelopmentMode()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            ExceptionHandler.handle(call, cause, developmentMode)
        }
    }
}

fun Application.isDevelopmentMode(): Boolean {
    val environment = this.environment
    return environment.config.propertyOrNull("ktor.deployment.environment")?.getString() == "development" ||
            environment.config.propertyOrNull("ktor.deployment.development")?.getString()?.toBoolean() == true ||
            environment.developmentMode
}