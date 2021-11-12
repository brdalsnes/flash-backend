package com.brdalsnes

import com.brdalsnes.repositories.UserTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun userTableSetup() {
    transaction {
        UserTable.insert {
            it[id] = UUID.fromString("d5ca4cd1-5a54-433a-8580-bbb110031c82")
            it[name] = "Green"
        }
        UserTable.insert {
            it[id] = UUID.fromString("d5ca4cd1-5a54-433a-8580-bbb110031c83")
            it[name] = "Red"
        }
    }
}