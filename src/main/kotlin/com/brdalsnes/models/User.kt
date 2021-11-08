package com.brdalsnes.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: String, val name: String, val score: Int)

@Serializable
data class  NewUser(val name: String)

@Serializable
data class  UpdateUser(val name: String, val score: Int)
