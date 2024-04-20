package ru.tinkoff.tinkoffer.data.models.projects.request

import kotlinx.serialization.Serializable

@Serializable
data class UserList(
    val userIds: List<String>
)
