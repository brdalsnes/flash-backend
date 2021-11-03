package com.brdalsnes.models

import kotlinx.serialization.Serializable

@Serializable
data class Card(val id: String, val deckId: String, val front: String, val back: String)
