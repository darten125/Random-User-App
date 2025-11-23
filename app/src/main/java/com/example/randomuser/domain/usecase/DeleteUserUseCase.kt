package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.repository.UserRepository

class DeleteUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String) =
        repository.deleteUserById(id)
}