package ru.tinkoff.tinkoffer.data.models.users.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String
)
