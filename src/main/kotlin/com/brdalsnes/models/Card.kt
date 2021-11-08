package com.brdalsnes.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Card(val id: String, val deckId: String, val front: String, val back: String)

@Serializable
data class NewCard(val deckId: String, val front: String, val back: String)

@Serializable
data class UpdateCard(val front: String, val back: String)

data class InsertCard(val deckId: UUID, val front: String, val back: String)
