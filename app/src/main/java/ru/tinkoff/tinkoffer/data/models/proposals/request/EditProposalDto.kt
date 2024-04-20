package ru.tinkoff.tinkoffer.data.models.proposals.request

import kotlinx.serialization.Serializable

@Serializable
data class EditProposalDto(
    val text: String,
    val jiraLink: String,
)
