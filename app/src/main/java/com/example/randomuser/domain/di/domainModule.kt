package com.example.randomuser.domain.di

import com.example.randomuser.domain.usecase.ClearAllUsersUseCase
import com.example.randomuser.domain.usecase.CreateUserUseCase
import com.example.randomuser.domain.usecase.DeleteUserUseCase
import com.example.randomuser.domain.usecase.GetUserDetailUseCase
import com.example.randomuser.domain.usecase.GetUserListUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CreateUserUseCase(get()) }
    single { GetUserListUseCase(get()) }
    single { GetUserDetailUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    single { ClearAllUsersUseCase( get()) }
}