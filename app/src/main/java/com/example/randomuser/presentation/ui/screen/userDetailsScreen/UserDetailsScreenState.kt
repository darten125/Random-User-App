package com.example.randomuser.presentation.ui.screen.userDetailsScreen

import com.example.randomuser.presentation.ui.components.userCard.UserDataUiModel

data class UserDetailsScreenState(
    val isLoading: Boolean = false,
    val userDetails: UserDataUiModel? =  null,
    val errorMessage: String? = null,
)