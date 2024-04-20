package ru.tinkoff.tinkoffer.data.models.projects.request

import kotlinx.serialization.Serializable


@Serializable
data class EditProjectDto(
    val name: String,
    val schedule: String,
    val votesPerPeriod: Int,
    val votesRefreshPeriodDays: Int
)
