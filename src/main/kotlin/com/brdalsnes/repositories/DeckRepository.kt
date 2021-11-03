package com.brdalsnes.repositories

import org.jetbrains.exposed.sql.Table

object DeckTable : Table("deck") {
    val id = uuid("id")
    val creator = uuid("creator").references(UserTable.id)
    val name = varchar("name", 64)
    override val primaryKey = PrimaryKey(id)
}