package com.brdalsnes.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Deck(val id: String, val creator: String, val name: String, val numSubscribers: Int, val numCards: Int)

@Serializable
data class NewDeck(val creator: String, val name: String)

@Serializable
data class UpdateDeck(val name: String)

data class InsertDeck(val creator: UUID, val name: String)
