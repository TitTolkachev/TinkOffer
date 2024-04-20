package ru.tinkoff.tinkoffer.data.models.proposals.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus


@Serializable
data class ProposalInfoDto(
    val id: String,
    val text: String,
    val user: UserInfoDto,
    val proposalStatus: ProposalStatus,
    val userVote: Boolean?,
    val canVote: Boolean,
    val jiraLink: String,
    val canBeVoteCanceled: Boolean,
    val comments: List<CommentDto>,
    val createdAt: String,
)
