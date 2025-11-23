package com.example.randomuser.presentation.ui.screen.userListScreen

import com.example.randomuser.presentation.ui.components.userListCard.UserListCardUiModel

data class UserListScreenState(
    val isLoading: Boolean = false,
    val users: List<UserListCardUiModel> = emptyList(),
    val errorMessage: String? = null,
)