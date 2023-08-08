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
package be.rubus.kotlin.exposed.db

import be.rubus.kotlin.exposed.DBSettings
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    DBSettings.db  //  setup connection for Exposed

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities, Users)

        val amsterdamId = Cities.insert {
            it[name] = "Amsterdam"
        } get Cities.id

        val sydneyId = Cities.insert {
            it[name] = "Sydney"
        } get Cities.id

        Users.insert {
            it[name] = "Emma"
            it[cityId] = amsterdamId
        }

        Users.insert {
            it[name] = "Liam"
            it[cityId] = amsterdamId
        }

        Users.insert {
            it[name] = "Olivia"
            it[cityId] = sydneyId
        }

        println("All cities:")

        for (city in Cities.selectAll()) {
            println("${city[Cities.id]}: ${city[Cities.name]}")
        }

        println("Join:")
        (Users innerJoin Cities).slice(Users.name, Cities.name).select { Users.name.eq("Emma") }.forEach {
            println("${it[Users.name]} works in ${it[Cities.name]}")
        }

        SchemaUtils.drop(Users, Cities)
    }
}