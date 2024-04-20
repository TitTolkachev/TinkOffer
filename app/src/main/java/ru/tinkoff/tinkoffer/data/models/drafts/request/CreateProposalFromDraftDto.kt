package ru.tinkoff.tinkoffer.data.models.drafts.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateProposalFromDraftDto(
    val draftId: String,
    val content: String,
    val jiraLink: String,
)
