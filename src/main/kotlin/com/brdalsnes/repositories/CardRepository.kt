package com.brdalsnes.repositories

import org.jetbrains.exposed.sql.Table

object CardTable : Table("card") {
    val id = uuid("id")
    val deckId = uuid("deckId").references(DeckTable.id)
    val front = varchar("front", 128)
    val back = varchar("back", 128)
    override val primaryKey = PrimaryKey(id)
}

class CardRepository {
}