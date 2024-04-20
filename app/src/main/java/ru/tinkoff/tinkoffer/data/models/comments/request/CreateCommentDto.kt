package ru.tinkoff.tinkoffer.data.models.comments.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentDto(
    val text: String
)
