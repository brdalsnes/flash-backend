package com.brdalsnes.services

import com.brdalsnes.models.Card
import com.brdalsnes.models.InsertCard
import com.brdalsnes.models.NewCard
import com.brdalsnes.models.UpdateCard
import com.brdalsnes.repositories.CardRepository
import com.brdalsnes.repositories.DeckRepository
import io.ktor.features.*
import java.util.*

class CardService {
    private val repository = CardRepository()

    suspend fun getAllInDeck(deckId: String): List<Card> {
        val deckUuid = UUID.fromString(deckId)
        DeckRepository().get(deckUuid) ?: throw NotFoundException("Deck not found")
        return repository.getAllInDeck(deckUuid)
    }

    suspend fun get(id: String): Card {
        val uuid = UUID.fromString(id)
        return repository.get(uuid) ?: throw NullPointerException()
    }

    suspend fun add(card: NewCard) {
        val deckId = UUID.fromString(card.deckId)
        DeckRepository().get(deckId) ?: throw NotFoundException("Deck not found")
        val insertCard = InsertCard(
            deckId = deckId,
            front = card.front,
            back = card.back
        )
        repository.add(insertCard)
    }

    suspend fun update(id: String, card: UpdateCard) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.update(uuid, card)
    }

    suspend fun delete(id: String) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.delete(uuid)
    }
}