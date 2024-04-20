package ru.tinkoff.tinkoffer.data.models.proposals.request

import kotlinx.serialization.Serializable

@Serializable
data class VoteRequest(
    val isUpvote: Boolean
)
