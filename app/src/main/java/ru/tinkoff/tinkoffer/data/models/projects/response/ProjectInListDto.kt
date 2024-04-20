package ru.tinkoff.tinkoffer.data.models.projects.response

import kotlinx.serialization.Serializable

@Serializable
data class ProjectInListDto(
    val id: String,
    val name: String,
    val participantCount: Long,
    val createdAt: Long,
)
