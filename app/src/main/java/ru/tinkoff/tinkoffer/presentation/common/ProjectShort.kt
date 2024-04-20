package ru.tinkoff.tinkoffer.presentation.common

import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInListDto

data class ProjectShort(
    val id: String,
    val name: String,
    val members: Int,
    val creationDate: Long,
)

val projects = listOf(
    ProjectInListDto(id = "1", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "2", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "3", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "4", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "5", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "6", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "7", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "8", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "9", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "10", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "11", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "12", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "13", name = "Project 1", participantCount = 1, createdAt = 23423423443),
    ProjectInListDto(id = "14", name = "Project 1", participantCount = 1, createdAt = 23423423443),
)