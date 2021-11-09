package com.brdalsnes.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Subscription(val id: String, val userId: String, val deckId: String)

@Serializable
data class NewSubscription(val userId: String, val deckId: String)

data class InsertSubscription(val userId: UUID, val deckId: UUID)