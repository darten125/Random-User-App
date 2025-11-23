package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserListUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> =
        repository.getAllCachedUsers()
}