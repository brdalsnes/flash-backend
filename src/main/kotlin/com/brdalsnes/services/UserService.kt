package com.brdalsnes.services

import com.brdalsnes.models.NewUser
import com.brdalsnes.models.UpdateUser
import com.brdalsnes.models.User
import com.brdalsnes.repositories.UserRepository
import io.ktor.features.*
import java.util.*

class UserService {
    private val repository = UserRepository()

    suspend fun getAll(): List<User> {
        return repository.getAll()
    }

    suspend fun get(id: String): User {
        val uuid = UUID.fromString(id)
        return repository.get((uuid)) ?: throw NotFoundException()
    }

    suspend fun add(user: NewUser) {
        repository.add(user)
    }

    suspend fun update(id: String, user: UpdateUser) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.update(uuid, user)
    }

    suspend fun delete(id: String) {
        val uuid = UUID.fromString(id)
        repository.get(uuid) ?: throw NotFoundException()
        repository.delete(uuid)
    }
}