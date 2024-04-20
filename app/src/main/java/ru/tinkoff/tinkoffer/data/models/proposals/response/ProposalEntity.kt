package ru.tinkoff.tinkoffer.data.models.proposals.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.projects.response.CommentEntity
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectEntity
import ru.tinkoff.tinkoffer.data.models.users.response.UserEntity
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus

@Serializable
data class ProposalEntity(
    val id: String,
    val text: String,
    val jiraLink: String,
    val votesFor: Int,
    val votesAgainst: Int,
    val user: UserEntity,
    val proposalStatus: ProposalStatus,
    val comments: List<CommentEntity>,
    val project: ProjectEntity,
    val createdAt: String
    )
