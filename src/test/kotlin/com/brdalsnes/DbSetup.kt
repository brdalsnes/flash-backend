package com.brdalsnes

import com.brdalsnes.repositories.CardTable
import com.brdalsnes.repositories.DeckTable
import com.brdalsnes.repositories.SubscriptionTable
import com.brdalsnes.repositories.UserTable
import org.jetbrains.exposed.sql.deleteAll
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

fun userTableClear() {
    transaction {
        UserTable.deleteAll()
    }
}

fun deckTableSetup() {
    transaction {
        DeckTable.insert {
            it[id] = UUID.fromString("a18e337d-4593-4239-9554-281794d58f43")
            it[creator] = UUID.fromString("d5ca4cd1-5a54-433a-8580-bbb110031c82")
            it[name] = "Spanish"
        }
        DeckTable.insert {
            it[id] = UUID.fromString("a18e337d-4593-4239-9554-281794d58f44")
            it[creator] = UUID.fromString("d5ca4cd1-5a54-433a-8580-bbb110031c82")
            it[name] = "French"
        }
    }
}

fun deckTableClear() {
    transaction {
        DeckTable.deleteAll()
    }
}

fun cardTableSetup() {
    transaction {
        CardTable.insert {
            it[id] = UUID.fromString("72ec667a-ef97-47b1-afcd-da00b27f9966")
            it[deckId] = UUID.fromString("a18e337d-4593-4239-9554-281794d58f43")
            it[front] = "Hello"
            it[back] = "Hola"
        }
        CardTable.insert {
            it[id] = UUID.fromString("72ec667a-ef97-47b1-afcd-da00b27f9967")
            it[deckId] = UUID.fromString("a18e337d-4593-4239-9554-281794d58f43")
            it[front] = "Goodbye"
            it[back] = "Adios"
        }
    }
}

fun cardTableClear() {
    transaction {
        CardTable.deleteAll()
    }
}

fun subscriptionTableSetup() {
    transaction {
        SubscriptionTable.insert {
            it[id] = UUID.fromString("3d2330af-d153-44e8-aa60-bc0051ffcc6e")
            it[userId] = UUID.fromString("d5ca4cd1-5a54-433a-8580-bbb110031c83")
            it[deckId] = UUID.fromString("a18e337d-4593-4239-9554-281794d58f43")
        }
    }
}

fun subscriptionTableClear() {
    transaction {
        SubscriptionTable.deleteAll()
    }
}

fun fullSetup() {
    userTableSetup()
    deckTableSetup()
    cardTableSetup()
    subscriptionTableSetup()
}

fun fullClear() {
    subscriptionTableClear()
    cardTableClear()
    deckTableClear()
    userTableClear()
}