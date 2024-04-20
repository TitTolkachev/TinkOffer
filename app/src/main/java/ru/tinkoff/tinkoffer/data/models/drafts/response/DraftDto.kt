package ru.tinkoff.tinkoffer.data.models.drafts.response

import kotlinx.serialization.Serializable

@Serializable
data class DraftDto(
    val id: String,
    val content: String,
    val jiraLink: String,
    val savedAt: String,
)
