package ru.tinkoff.tinkoffer.data.models.projects.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.users.response.ProjectUserInfoDto

@Serializable
data class ProjectInfoDto(
    val id: String,
    val name: String,
    val schedule: String,
    val votesPerPeriod: Long,
    val votesRefreshPeriodDays: Long,
    val createdAt: String,
    val users: List<ProjectUserInfoDto>,
)
