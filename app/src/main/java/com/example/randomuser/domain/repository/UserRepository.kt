package com.example.randomuser.domain.repository

import com.example.randomuser.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun generateAndCacheUser(
        gender: String,
        nationality: String
    ): Result<User>
    fun getAllCachedUsers(): Flow<List<User>>
    fun getUserById(id: String): Flow<User>
    suspend fun deleteUserById(id: String)
    suspend fun clearAllUsers()
}