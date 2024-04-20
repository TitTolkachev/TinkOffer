package ru.tinkoff.tinkoffer.data.models.projects.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectDto(
    val name: String,
    val schedule: String,
    val votesPerPeriod: Int,
    val votesRefreshPeriodDays: Int,
)
