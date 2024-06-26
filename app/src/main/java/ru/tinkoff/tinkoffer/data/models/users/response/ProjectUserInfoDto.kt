package ru.tinkoff.tinkoffer.data.models.users.response

import kotlinx.serialization.Serializable

@Serializable
data class ProjectUserInfoDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phone: String,
    val avatarNumber: Int,
    val availableVotes: Long,
    val isAdmin: Boolean,
)
