package com.example.randomuser.data.repository

import com.example.randomuser.data.local.dao.UserDao
import com.example.randomuser.data.mapper.toDomain
import com.example.randomuser.data.mapper.toEntity
import com.example.randomuser.data.remote.api.RandomUserApi
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val api: RandomUserApi,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun generateAndCacheUser(
        gender: String,
        nationality: String
    ): Result<User> = try {
        val userDto = api.getRandomUser(gender = gender, nationality = nationality).results.firstOrNull()
        val entity = userDto?.toEntity()
        userDao.insert(entity!!)
        Result.success(entity.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getAllCachedUsers(): Flow<List<User>> =
        userDao.getAllUsers().map { it.toDomain() }

    override fun getUserById(id: String): Flow<User> =
        userDao.getUserById(id).map { it.toDomain() }

    override suspend fun deleteUserById(id: String) {
        userDao.deleteUserById(id)
    }

    override suspend fun clearAllUsers() {
        userDao.clearAllUsers()
    }
}