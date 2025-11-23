package com.example.randomuser.presentation.ui.screen.userCreateScreen

data class UserCreateScreenState(
    val isLoading: Boolean = false,
    val selectedGender: String? = null,
    val selectedNationality: String? = null,
    val isUserCreated: Boolean = false,
    val errorMessage: String? = null,
)
