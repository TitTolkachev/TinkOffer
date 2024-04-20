package ru.tinkoff.tinkoffer.data.models.drafts.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateDraftDto(
    val content: String,
    val jiraLink: String
)
