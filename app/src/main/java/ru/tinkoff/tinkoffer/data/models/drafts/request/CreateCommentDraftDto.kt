package ru.tinkoff.tinkoffer.data.models.drafts.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentDraftDto(
    val text: String,
    val proposalId: String,
    val commentId: String,
)
