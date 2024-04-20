package ru.tinkoff.tinkoffer.data.models.proposals.request

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus

@Serializable
data class ChangeProposalStatus(
    val proposalStatus: ProposalStatus
)
