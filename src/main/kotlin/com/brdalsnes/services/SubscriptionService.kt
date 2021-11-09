package com.brdalsnes.services

import com.brdalsnes.models.InsertSubscription
import com.brdalsnes.models.NewSubscription
import com.brdalsnes.repositories.DeckRepository
import com.brdalsnes.repositories.SubscriptionRepository
import com.brdalsnes.repositories.UserRepository
import io.ktor.features.*
import java.util.*

class SubscriptionService {
    private val repository = SubscriptionRepository()

    suspend fun add(subscription: NewSubscription) {
        val userId = UUID.fromString(subscription.userId)
        UserRepository().get(userId) ?: throw NotFoundException("User not found")
        val deckId = UUID.fromString(subscription.deckId)
        DeckRepository().get(deckId) ?: throw NotFoundException("Deck not found")
        val insertSubscription = InsertSubscription(
            userId = userId,
            deckId = deckId
        )
        repository.add(insertSubscription)
    }

    suspend fun delete(id: String) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.delete(uuid)
    }
}