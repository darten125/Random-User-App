package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository

class CreateUserUseCase (
    private val repository: UserRepository
    ) {
    suspend operator fun invoke(
        gender: String,
        nationality: String
    ): Result<User> {
        return repository.generateAndCacheUser(gender, nationality)
    }
}