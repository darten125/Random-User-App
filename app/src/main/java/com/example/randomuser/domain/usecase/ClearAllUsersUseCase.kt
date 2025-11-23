package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.repository.UserRepository

class ClearAllUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke() =
        repository.clearAllUsers()
}