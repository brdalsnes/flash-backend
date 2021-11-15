package com.brdalsnes.repositories

import com.brdalsnes.DatabaseFactory.dbQuery
import com.brdalsnes.models.InsertSubscription
import com.brdalsnes.models.Subscription
import org.jetbrains.exposed.sql.*
import java.util.*

object SubscriptionTable : Table("subscription") {
    val id = uuid("id").autoGenerate()
    val userId = uuid("userId").references(UserTable.id)
    val deckId = uuid("deckId").references(DeckTable.id)
    override val primaryKey = PrimaryKey(id)
}

class SubscriptionRepository {
    suspend fun get(id: UUID) = dbQuery {
        SubscriptionTable.select { SubscriptionTable.id eq id }.map { toSubscription(it) }.singleOrNull()
    }

    suspend fun getAllForUser(userId: UUID) = dbQuery {
        SubscriptionTable.select { SubscriptionTable.userId eq userId }.map { toSubscription(it) }
    }

    suspend fun getAllForDeck(deckId: UUID) = dbQuery {
        SubscriptionTable.select { SubscriptionTable.deckId eq deckId }.map { toSubscription(it) }
    }

    suspend fun getCountForDeck(deckId: UUID) = dbQuery {
        SubscriptionTable.select { SubscriptionTable.deckId eq deckId }.count().toInt()
    }

    suspend fun add(subscription: InsertSubscription) = dbQuery {
        SubscriptionTable.insert {
            it[userId] = subscription.userId
            it[deckId] = subscription.deckId
        }
    }

    suspend fun delete(id: UUID) = dbQuery {
        SubscriptionTable.deleteWhere { SubscriptionTable.id eq id }
    }

    suspend fun deleteAllForUser(userId: UUID) = dbQuery {
        SubscriptionTable.deleteWhere { SubscriptionTable.userId eq userId }
    }

    suspend fun deleteAllForDeck(deckId: UUID) = dbQuery {
        SubscriptionTable.deleteWhere { SubscriptionTable.deckId eq deckId }
    }

    private fun toSubscription(row: ResultRow): Subscription {
        return Subscription(
            id = row[SubscriptionTable.id].toString(),
            userId = row[SubscriptionTable.userId].toString(),
            deckId = row[SubscriptionTable.deckId].toString()
        )
    }
}