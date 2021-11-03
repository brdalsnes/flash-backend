package com.brdalsnes.models

import kotlinx.serialization.Serializable

@Serializable
data class Deck(val id: String, val creator: String, val name: String)
