package com.brdalsnes.services

import com.brdalsnes.models.*
import com.brdalsnes.repositories.SubscriptionRepository
import com.brdalsnes.repositories.UserRepository
import io.ktor.features.*
import java.util.*

class UserService {
    private val repository = UserRepository()

    suspend fun getAll(): List<User> {
        return repository.getAll()
    }

    suspend fun get(id: String): User {
        val uuid = UUID.fromString(id)
        return repository.get(uuid) ?: throw NotFoundException()
    }

    suspend fun getSubscriptions(id: String): List<Subscription> {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        return SubscriptionRepository().getAllForUser(uuid)
    }

    suspend fun getDecks(id: String): List<Deck> {
        val subscriptions = getSubscriptions(id)
        val deckService = DeckService()
        return subscriptions.map { deckService.get(it.deckId) }
    }

    suspend fun add(user: NewUser) {
        repository.add(user)
    }

    suspend fun update(id: String, user: UpdateUser) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.update(uuid, user)
    }

    suspend fun delete(id: String) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.delete(uuid)
        SubscriptionRepository().deleteAllForUser(uuid)
    }
}