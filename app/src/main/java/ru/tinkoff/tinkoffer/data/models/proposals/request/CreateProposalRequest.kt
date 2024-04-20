package ru.tinkoff.tinkoffer.data.models.proposals.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateProposalRequest(
    val text: String,
    val projectId: String,
    val jiraLink: String
)
