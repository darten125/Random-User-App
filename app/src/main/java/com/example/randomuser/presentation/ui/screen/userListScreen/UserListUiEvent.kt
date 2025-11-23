package com.example.randomuser.presentation.ui.screen.userListScreen

sealed interface UserListUiEvent {
    data class DeleteUser(val id: String): UserListUiEvent
    object ClearAllUsers: UserListUiEvent
    object RetryLoad: UserListUiEvent
}