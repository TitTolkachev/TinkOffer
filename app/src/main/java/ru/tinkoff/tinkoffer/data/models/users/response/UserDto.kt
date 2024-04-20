package ru.tinkoff.tinkoffer.data.models.users.response

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val phone: String? = null,
    val avatarNumber: Int
)
