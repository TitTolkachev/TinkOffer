package ru.tinkoff.tinkoffer.data.models.proposals.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus

@Serializable
data class ProposalInListDto(
    val id: String,
    val text: String,
    val user: UserDto,
    val userVote: Boolean?,
    val votesFor: Long,
    val votesAgainst: Long,
    val proposalStatus: ProposalStatus,
    val createdAt: String
)
