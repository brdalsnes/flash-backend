package com.brdalsnes.repositories

import com.brdalsnes.DatabaseFactory.dbQuery
import com.brdalsnes.models.Deck
import com.brdalsnes.models.InsertDeck
import com.brdalsnes.models.UpdateDeck
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import java.util.*

object DeckTable : Table("deck") {
    val id = uuid("id").autoGenerate()
    val creator = uuid("creator").references(UserTable.id)
    val name = varchar("name", 64)
    override val primaryKey = PrimaryKey(id)
}

class DeckRepository {
    suspend fun getAll() = dbQuery {
        runBlocking {
            DeckTable.selectAll().map { toDeck(it) }
        }
    }

    suspend fun get(id: UUID) = dbQuery {
        runBlocking {
            DeckTable.select { DeckTable.id eq id }.map { toDeck(it) }.singleOrNull()
        }
    }

    suspend fun add(deck: InsertDeck) = dbQuery {
        DeckTable.insert {
            it[creator] = deck.creator
            it[name] = deck.name
        }
    }

    suspend fun update(id: UUID, deck: UpdateDeck) = dbQuery {
        DeckTable.update({ DeckTable.id eq id }) {
            it[name] = deck.name
        }
    }

    suspend fun delete(id: UUID) = dbQuery {
        DeckTable.deleteWhere { DeckTable.id eq id }
    }

    private suspend fun toDeck(row: ResultRow): Deck {
        return Deck(
            id = row[DeckTable.id].toString(),
            creator = row[DeckTable.creator].toString(),
            name = row[DeckTable.name],
            numSubscribers = SubscriptionRepository().getCountForDeck(row[DeckTable.id]),
            numCards = CardRepository().getCountForDeck(row[DeckTable.id])
        )
    }
}

