package com.brdalsnes.repositories

import com.brdalsnes.DatabaseFactory.dbQuery
import com.brdalsnes.models.Card
import com.brdalsnes.models.InsertCard
import com.brdalsnes.models.UpdateCard
import org.jetbrains.exposed.sql.*
import java.util.*

object CardTable : Table("card") {
    val id = uuid("id").autoGenerate()
    val deckId = uuid("deckId").references(DeckTable.id)
    val front = varchar("front", 128)
    val back = varchar("back", 128)
    override val primaryKey = PrimaryKey(id)
}

class CardRepository {
    suspend fun get(id: UUID) = dbQuery {
        CardTable.select { CardTable.id eq id }.map { toCard(it) }.singleOrNull()
    }

    suspend fun getAllInDeck(deckId: UUID) = dbQuery {
        CardTable.select { CardTable.deckId eq deckId }.map { toCard(it) }
    }

    suspend fun getCountForDeck(deckId: UUID) = dbQuery {
        CardTable.select { CardTable.deckId eq deckId }.count().toInt()
    }

    suspend fun add(card: InsertCard) = dbQuery {
        CardTable.insert {
            it[deckId] = card.deckId
            it[front] = card.front
            it[back] = card.back
        }
    }

    suspend fun update(id: UUID, card: UpdateCard) = dbQuery {
        CardTable.update({ CardTable.id eq id}) {
            it[front] = card.front
            it[back] = card.back
        }
    }

    suspend fun delete(id: UUID) = dbQuery {
        CardTable.deleteWhere { CardTable.id eq id }
    }

    private fun toCard(row: ResultRow): Card {
        return Card(
            id = row[CardTable.id].toString(),
            deckId = row[CardTable.deckId].toString(),
            front = row[CardTable.front],
            back = row[CardTable.back]
        )
    }
}