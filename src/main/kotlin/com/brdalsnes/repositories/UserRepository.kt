package com.brdalsnes.repositories

import org.jetbrains.exposed.sql.Table

object UserTable : Table("card_user") {
    val id = uuid("id")
    val name = varchar("name", 64)
    val score = integer("score")
    override val primaryKey = PrimaryKey(id)
}