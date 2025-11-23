package com.example.randomuser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.usecase.ClearAllUsersUseCase
import com.example.randomuser.domain.usecase.DeleteUserUseCase
import com.example.randomuser.domain.usecase.GetUserListUseCase
import com.example.randomuser.presentation.ui.components.userListCard.UserListCardUiModel
import com.example.randomuser.presentation.ui.screen.userListScreen.UserListScreenState
import com.example.randomuser.presentation.ui.screen.userListScreen.UserListUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListViewModel(
    private val getUserListUseCase: GetUserListUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val clearAllUsersUseCase: ClearAllUsersUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(UserListScreenState())
    val state = _state.asStateFlow()

    init {
        observeUsers()
    }

    fun onEvent(event: UserListUiEvent) {
        when (event) {
            is UserListUiEvent.DeleteUser -> onDeleteRequested(event.id)
            is UserListUiEvent.ClearAllUsers -> onClearAllRequested()
            is UserListUiEvent.RetryLoad -> observeUsers()
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                getUserListUseCase().collectLatest { domainList ->
                    val uiList = domainList.map { user ->
                        UserListCardUiModel(
                            id = user.uuid,
                            name = user.fullName,
                            phone = user.phone,
                            city = user.city,
                            nationality = user.nationality,
                            urlToImage = user.pictureMedium
                        )
                    }
                    _state.value = _state.value.copy(
                        users = uiList,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (t: Throwable) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = t.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    private fun onDeleteRequested(id: String) {
        viewModelScope.launch {
            try {
                deleteUserUseCase(id)
            } catch (e: Throwable) {
                _state.value = _state.value.copy(
                    errorMessage = "Ошибка удаления: ${e.localizedMessage}"
                )
            }
        }
    }

    private fun onClearAllRequested() {
        viewModelScope.launch {
            try {
                clearAllUsersUseCase()
            } catch (e: Throwable) {
                _state.value = _state.value.copy(
                    errorMessage = "Ошибка очистки: ${e.localizedMessage}"
                )
            }
        }
    }
}