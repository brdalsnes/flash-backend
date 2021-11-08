package com.brdalsnes.repositories

import com.brdalsnes.DatabaseFactory.dbQuery
import com.brdalsnes.models.NewUser
import com.brdalsnes.models.UpdateUser
import com.brdalsnes.models.User
import org.jetbrains.exposed.sql.*
import java.util.*

object UserTable : Table("card_user") {
    val id = uuid("id").autoGenerate()
    val name = varchar("name", 64).uniqueIndex()
    val score = integer("score").default(0)
    override val primaryKey = PrimaryKey(id)
}

class UserRepository {
    suspend fun getAll() = dbQuery {
        UserTable.selectAll().map { toUser(it) }
    }

    suspend fun get(id: UUID) = dbQuery {
        UserTable.select { UserTable.id eq id }.map { toUser(it) }.singleOrNull()
    }

    suspend fun add(user: NewUser) = dbQuery {
        UserTable.insert {
            it[name] = user.name
        }
    }

    suspend fun update(id: UUID, user: UpdateUser) = dbQuery {
        UserTable.update({ UserTable.id eq id }) {
            it[name] = user.name
            it[score] = user.score
        }
    }

    suspend fun delete(id: UUID) = dbQuery {
        UserTable.deleteWhere { UserTable.id eq id }
    }

    private fun toUser(row: ResultRow): User {
        return User(
            id = row[UserTable.id].toString(),
            name = row[UserTable.name],
            score = row[UserTable.score]
        )
    }
}