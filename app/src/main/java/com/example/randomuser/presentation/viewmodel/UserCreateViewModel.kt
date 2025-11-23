package com.example.randomuser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.usecase.CreateUserUseCase
import com.example.randomuser.presentation.ui.screen.userCreateScreen.UserCreateScreenState
import com.example.randomuser.presentation.ui.screen.userCreateScreen.UserCreateUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserCreateViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserCreateScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: UserCreateUiEvent) {
        when (event) {
            is UserCreateUiEvent.SelectGender -> onGenderSelected(event.gender)
            is UserCreateUiEvent.SelectNationality -> onNationalitySelected(event.nat)
            is UserCreateUiEvent.CreateUser -> createUser()

        }
    }
    private fun onGenderSelected(gender: String) {
        _state.value = _state.value.copy(
            selectedGender = gender,
            errorMessage = null
        )
    }
    private fun onNationalitySelected(nationality: String) {
        _state.value = _state.value.copy(
            selectedNationality = nationality,
            errorMessage = null
        )
    }
    private fun createUser() {
        val current = _state.value
        if (current.selectedGender.isNullOrBlank() || current.selectedNationality.isNullOrBlank()) {
            _state.update {
                it.copy(errorMessage = "Выберите пол и национальность")
            }
            return
        }
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                val result = createUserUseCase(
                    gender = current.selectedGender!!,
                    nationality = current.selectedNationality!!
                )
                result.fold(
                    onSuccess = {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isUserCreated = true
                            )
                        }
                    },
                    onFailure = { exception ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = exception.message ?: "Ошибка создания пользователя"
                            )
                        }
                    }
                )
            } catch (t: Throwable) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = t.message ?: "Неизвестная ошибка"
                    )
                }
            }
        }
    }
}