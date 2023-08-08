/**
 * Copyright 2023 Rudy De Busscher (https://www.atbash.be)
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
package be.rubus.kotlin.exposed.dao

import be.rubus.kotlin.exposed.DBSettings
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    DBSettings.db  //  setup connection for Exposed

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities, Users)

        val amsterdam = City.new {
            name = "Amsterdam"
        }

        val sydney = City.new {
            name = "Sydney"
        }

        println("All cities:")

        for (city in City.all()) {
            println("${city.id}: ${city.name}")
        }


        User.new {
            name = "Emma"
            city = amsterdam
        }

        User.new {
            name = "Liam"
            city = amsterdam
        }

        User.new {
            name = "Olivia"
            city = sydney
        }


        println("join:")

        // Lazy loading
        User.find { Users.name.eq("Emma") }.forEach {
            println("${it.name} works in ${it.city.name}")
        }


        // Eager loading
        val query = Users.innerJoin(Cities).select(
            Users.name.eq("Emma")
        )

        User.wrapRows(query).forEach {
            println("${it.name} works in ${it.city.name}")
        }

        SchemaUtils.drop(Users, Cities)
    }
}