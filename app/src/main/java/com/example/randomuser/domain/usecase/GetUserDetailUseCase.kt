package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserDetailUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String): Flow<User> =
        repository.getUserById(userId)
}