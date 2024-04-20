package ru.tinkoff.tinkoffer.data.models.drafts.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateDraftDto(
    val content: String,
    val jiraLink: String,
    val projectId: String,
)
