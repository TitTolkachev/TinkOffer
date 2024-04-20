package ru.tinkoff.tinkoffer.data.models.users.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeAvatarRequest(
    val avatarNumber: Int
)
