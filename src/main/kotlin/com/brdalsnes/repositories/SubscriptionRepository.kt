package com.brdalsnes.repositories

import com.brdalsnes.repositories.CardTable.references
import org.jetbrains.exposed.sql.Table

object SubscriptionTable : Table("subscription") {
    val id = uuid("id")
    val userId = uuid("userId").references(UserTable.id)
    val deckId = uuid("deckId").references(DeckTable.id)
    override val primaryKey = PrimaryKey(id)
}