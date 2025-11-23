package com.example.randomuser.presentation.ui.screen.userCreateScreen

sealed interface UserCreateUiEvent {
    data class SelectGender(val gender: String): UserCreateUiEvent
    data class SelectNationality(val nat: String): UserCreateUiEvent
    object CreateUser: UserCreateUiEvent
}