package com.example.randomuser.domain.model

data class User(
    val uuid: String,

    val gender: String,
    val fullName: String,
    val title: String,
    val firstName: String,
    val lastName: String,

    val email: String,
    val phone: String,
    val cell: String,

    val country: String,
    val city: String,
    val state: String,
    val fullStreet: String,
    val postcode: String,

    val latitude: String,
    val longitude: String,

    val age: Int,
    val birthDate: String,

    val nationality: String,

    val pictureLarge: String,
    val pictureMedium: String,
    val pictureThumbnail: String,

    val timestamp: Long
)