package ru.tinkoff.tinkoffer.data.models.proposals.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.projects.response.CommentEntity
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus

@Serializable
data class ProposalDto(
    val id: String,
    val text: String,
    val user: UserDto,
    val votesFor: Int,
    val votesAgainst: Int,
    val proposalStatus: ProposalStatus,
    val comments: List<CommentEntity>,
    val createdAt: String
)
