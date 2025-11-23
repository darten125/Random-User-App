package com.example.randomuser.data.mapper

import com.example.randomuser.data.local.entity.UserEntity
import com.example.randomuser.data.remote.dto.UserDto
import com.example.randomuser.domain.model.User
import java.util.UUID

fun UserDto.toEntity(): UserEntity = UserEntity(
    uuid = UUID.randomUUID().toString(),

    gender = gender.orEmpty(),
    title = name?.title.orEmpty(),
    firstName = name?.first.orEmpty(),
    lastName = name?.last.orEmpty(),

    email = email.orEmpty(),

    phone = phone.orEmpty(),
    cell = cell.orEmpty(),

    country = location?.country.orEmpty(),
    city = location?.city.orEmpty(),
    state = location?.state.orEmpty(),
    streetNumber = location?.street?.number ?: 0,
    streetName = location?.street?.name.orEmpty(),
    postcode = location?.postcode.toString(),

    latitude = location?.coordinates?.latitude.orEmpty(),
    longitude = location?.coordinates?.longitude.orEmpty(),

    timezoneOffset = location?.timezone?.offset.orEmpty(),
    timezoneDescription = location?.timezone?.description.orEmpty(),

    birthDate = dob?.date.orEmpty(),
    age = dob?.age ?: 0,

    idName = id?.name.orEmpty(),
    idValue = id?.value.orEmpty(),

    pictureLarge = picture?.large.orEmpty(),
    pictureMedium = picture?.medium.orEmpty(),
    pictureThumbnail = picture?.thumbnail.orEmpty(),

    nationality = nat.orEmpty()
)

fun UserEntity.toDomain(): User = User(
    uuid = uuid,

    gender = gender,
    fullName = "$title $firstName $lastName".trim(),
    title = title,
    firstName = firstName,
    lastName = lastName,

    email = email,
    phone = phone,
    cell = cell,

    country = country,
    city = city,
    state = state,
    fullStreet = "$streetNumber $streetName",
    postcode = postcode,

    latitude = latitude,
    longitude = longitude,

    age = age,
    birthDate = birthDate,

    nationality = nationality,

    pictureLarge = pictureLarge,
    pictureMedium = pictureMedium,
    pictureThumbnail = pictureThumbnail,

    timestamp = timestamp
)

fun List<UserEntity>.toDomain(): List<User> = map { it.toDomain() }