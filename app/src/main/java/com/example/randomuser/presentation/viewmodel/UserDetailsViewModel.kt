package com.example.randomuser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.usecase.GetUserDetailUseCase
import com.example.randomuser.presentation.ui.components.userCard.UserDataUiModel
import com.example.randomuser.presentation.ui.components.userCard.locationDataUiModel
import com.example.randomuser.presentation.ui.components.userCard.personDataUiModel
import com.example.randomuser.presentation.ui.components.userCard.phoneDataUiModel
import com.example.randomuser.presentation.ui.screen.userDetailsScreen.UserDetailsScreenState
import com.example.randomuser.presentation.ui.screen.userDetailsScreen.UserDetailsUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val userId: String,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserDetailsScreenState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        loadUser()
    }

    fun onEvent(event: UserDetailsUiEvent) {
        when (event) {
            is UserDetailsUiEvent.RetryLoad -> RetryLoad()

        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)

                getUserDetailUseCase(userId).collectLatest { userData ->
                    val uiModel = UserDataUiModel(
                        personData = personDataUiModel(
                            fullName = userData.fullName,
                            firstName = userData.firstName,
                            lastName = userData.lastName,
                            age = userData.age,
                            birthDate = userData.birthDate,
                            nationality = userData.nationality,
                            gender = userData.gender
                        ),
                        phoneData = phoneDataUiModel(
                            phone = userData.phone,
                            cell = userData.cell
                        ),
                        email = userData.email,
                        locationData = locationDataUiModel(
                            country = userData.country,
                            city = userData.city,
                            state = userData.state,
                            fullStreet = userData.fullStreet,
                            postcode = userData.postcode
                        ),
                        urlToImage = userData.pictureMedium
                    )

                    _state.value = _state.value.copy(
                        isLoading = false,
                        userDetails = uiModel,
                        errorMessage = null
                    )
                }
            } catch (t: Throwable) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = t.message ?: "Не удалось загрузить пользователя"
                )
            }
        }
    }

    private fun RetryLoad() {
        loadUser()
    }
}