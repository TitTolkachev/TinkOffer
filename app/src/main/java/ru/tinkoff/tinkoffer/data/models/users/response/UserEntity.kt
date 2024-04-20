package ru.tinkoff.tinkoffer.data.models.users.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectEntity

@Serializable
data class UserEntity(
    val id: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phone: String,
    val avatarNumber: Int,
    val projects: List<ProjectEntity>
)
