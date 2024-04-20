package ru.tinkoff.tinkoffer.presentation.common

data class ProjectShort(
    val id: String,
    val name: String,
    val members: Int,
    val creationDate: Long,
)

val projects = listOf(
    ProjectShort(id = "1", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "2", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "3", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "4", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "5", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "6", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "7", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "8", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "9", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "10", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "11", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "12", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "13", name = "Project 1", members = 1, creationDate = 23423423443),
    ProjectShort(id = "14", name = "Project 1", members = 1, creationDate = 23423423443),
)