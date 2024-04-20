package ru.tinkoff.tinkoffer.data.models.projects.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalEntity
import ru.tinkoff.tinkoffer.data.models.users.response.UserEntity

@Serializable
data class ProjectEntity(
    val id: String,
    val name: String,
    val description: String,
    val schedule: String,
    val votesPerPeriod: Long,
    val votesRefreshPeriodDays: Long,
    val createdAt: String,
    val users: List<UserEntity>,
    val proposals: List<ProposalEntity>,
)
