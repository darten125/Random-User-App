package com.example.randomuser.presentation.ui.components.userCard

data class UserDataUiModel(
    val personData: personDataUiModel,
    val phoneData: phoneDataUiModel,
    val email: String,
    val locationData: locationDataUiModel,
    val urlToImage: String
)

data class personDataUiModel(
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val birthDate: String,
    val nationality: String,
    val gender: String,
)

data class phoneDataUiModel(
    val phone: String,
    val cell: String,
)

data class locationDataUiModel(
    val country: String,
    val city: String,
    val state: String,
    val fullStreet: String,
    val postcode: String,
)
