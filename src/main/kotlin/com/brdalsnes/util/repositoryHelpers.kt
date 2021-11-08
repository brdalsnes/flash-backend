package com.brdalsnes.util

import io.ktor.features.*
import java.util.*

fun <T> getItemFromId(id: String, getFunction: (uuid: UUID) -> T?): T {
    val uuid = UUID.fromString(id)
    return getFunction(uuid) ?: throw NotFoundException()
}