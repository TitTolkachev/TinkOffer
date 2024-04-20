package ru.tinkoff.tinkoffer.presentation.common

data class ProposalShort(
    val id: String,
    val text: String,
    val likes: Int,
    val dislikes: Int,
    val user: UserShort,
)

val users = listOf(
    UserShort(id = "0", avatar = 1, nickname = "Егор Шамов"),
    UserShort(id = "1", avatar = 2, nickname = "Тит Толкачев"),
    UserShort(id = "2", avatar = 1, nickname = "Иванов Иван"),
    UserShort(id = "3", avatar = 4, nickname = "Олег"),
    UserShort(id = "4", avatar = 1, nickname = "Android"),
)

val proposals = listOf(
    ProposalShort(id = "0", "Очень крутое предложение конфетка", 123, 11, user = users.random()),
    ProposalShort(id = "1", "Очень крутое предложение конфетка", 1, 11, user = users.random()),
    ProposalShort(id = "2", "Очень крутое предложение конфетка", 2, 11, user = users.random()),
    ProposalShort(id = "3", "Очень крутое предложение конфетка", 3, 11, user = users.random()),
    ProposalShort(id = "4", "Очень крутое предложение конфетка", 0, 11, user = users.random()),
    ProposalShort(id = "5", "Очень крутое предложение конфетка", 123, 11, user = users.random()),
    ProposalShort(id = "6", "Очень крутое предложение конфетка", 0, 0, user = users.random()),
    ProposalShort(id = "7", "Очень крутое предложение конфетка", 0, 11, user = users.random()),
    ProposalShort(id = "8", "Очень крутое предложение конфетка", 123, 11, user = users.random()),
    ProposalShort(id = "9", "Очень крутое предложение конфетка", 123, 11, user = users.random()),
)