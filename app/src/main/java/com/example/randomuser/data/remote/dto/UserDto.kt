package com.example.randomuser.data.remote.dto

data class ApiResponse(
    val results: List<UserDto>
)

data class UserDto(
    val gender: String?,
    val name: NameDto?,
    val location: LocationDto?,
    val email: String?,
    val dob: DobDto?,
    val phone: String?,
    val cell: String?,
    val id: IdDto?,
    val picture: PictureDto?,
    val nat: String?
)

data class NameDto(
    val title: String?,
    val first: String?,
    val last: String?
)

data class LocationDto(
    val street: StreetDto?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: Any?, // может быть String или Int
    val coordinates: CoordinatesDto?,
    val timezone: TimezoneDto?
)

data class StreetDto(
    val number: Int?,
    val name: String?
)

data class CoordinatesDto(
    val latitude: String?,
    val longitude: String?
)

data class TimezoneDto(
    val offset: String?,
    val description: String?
)

data class DobDto(
    val date: String?,
    val age: Int?
)

data class IdDto(
    val name: String?,
    val value: String?
)

data class PictureDto(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
)