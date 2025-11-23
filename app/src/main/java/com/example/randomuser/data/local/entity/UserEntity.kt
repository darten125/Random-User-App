package com.example.randomuser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uuid: String,
    val gender: String,
    val title: String,
    val firstName: String,
    val lastName: String,

    val email: String,

    val phone: String,
    val cell: String,

    val country: String,
    val city: String,
    val state: String,
    val streetNumber: Int,
    val streetName: String,
    val postcode: String,

    val latitude: String,
    val longitude: String,

    val timezoneOffset: String,
    val timezoneDescription: String,

    val birthDate: String,
    val age: Int,

    val idName: String,
    val idValue: String?,

    val pictureLarge: String,
    val pictureMedium: String,
    val pictureThumbnail: String,

    val nationality: String,

    val timestamp: Long = System.currentTimeMillis()
)
