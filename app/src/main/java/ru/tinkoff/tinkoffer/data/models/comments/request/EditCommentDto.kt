package ru.tinkoff.tinkoffer.data.models.comments.request

import kotlinx.serialization.Serializable

@Serializable
data class EditCommentDto(
    val text: String
)
