package com.brdalsnes

import com.brdalsnes.repositories.CardTable
import com.brdalsnes.repositories.DeckTable
import com.brdalsnes.repositories.SubscriptionTable
import com.brdalsnes.repositories.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactoryTest {

    fun init() {
        Database.connect(hikari()) // 1

        // 2
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(DeckTable)
            SchemaUtils.create(CardTable)
            SchemaUtils.create(SubscriptionTable)
        }
    }
    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1;MODE=PostgreSQL"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}