package com.example.randomuser.presentation.di

import com.example.randomuser.presentation.viewmodel.UserCreateViewModel
import com.example.randomuser.presentation.viewmodel.UserDetailsViewModel
import com.example.randomuser.presentation.viewmodel.UserListViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { UserListViewModel(get(), get(), get()) }
    viewModel { UserCreateViewModel(get()) }
    viewModel { parameters ->
        UserDetailsViewModel(
            userId = parameters.get(),
            getUserDetailUseCase = get()
        )
    }
}