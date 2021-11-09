package com.brdalsnes.services

import com.brdalsnes.models.Deck
import com.brdalsnes.models.InsertDeck
import com.brdalsnes.models.NewDeck
import com.brdalsnes.models.UpdateDeck
import com.brdalsnes.repositories.DeckRepository
import com.brdalsnes.repositories.SubscriptionRepository
import com.brdalsnes.repositories.UserRepository
import io.ktor.features.*
import java.util.*

class DeckService {
    private val repository = DeckRepository()

    suspend fun getAll(): List<Deck> {
        return repository.getAll()
    }

    suspend fun get(id: String): Deck {
        val uuid = UUID.fromString(id)
        return repository.get(uuid) ?: throw NotFoundException()
    }

    suspend fun add(deck: NewDeck) {
        val creatorId = UUID.fromString(deck.creator)
        UserRepository().get(creatorId) ?: throw NotFoundException("User not found")
        val insertDeck = InsertDeck(
            creator = UUID.fromString(deck.creator),
            name = deck.name
        )
        repository.add(insertDeck)
    }

    suspend fun update(id: String, deck: UpdateDeck) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.update(uuid, deck)
    }

    suspend fun delete(id: String) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.delete(uuid)
        SubscriptionRepository().deleteAllForDeck(uuid)
    }
}