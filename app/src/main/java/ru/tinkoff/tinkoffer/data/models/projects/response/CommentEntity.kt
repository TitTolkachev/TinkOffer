package ru.tinkoff.tinkoffer.data.models.projects.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalEntity

@Serializable
data class CommentEntity(
    val id: String,
    val text: String,
    val proposal: ProposalEntity,
    val createdAt: String,
)
