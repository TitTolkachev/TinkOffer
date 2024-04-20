package ru.tinkoff.tinkoffer.presentation.common

import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto

data class ProposalShort(
    val id: String,
    val text: String,
    val likes: Int,
    val voice: Boolean? = null,
    val dislikes: Int,
    val user: UserShort,
)

val users = listOf(
    UserDto(id = "0", avatarNumber = 1, firstName = "Егор", lastName = "Шамов"),
    UserDto(id = "1", avatarNumber = 2, firstName = "Тит", lastName = "Толкачев"),
    UserDto(id = "2", avatarNumber = 1, firstName = "Иванов", lastName = "Иван"),
    UserDto(id = "3", avatarNumber = 4, firstName = "Олег", lastName = ""),
    UserDto(id = "4", avatarNumber = 1, firstName = "Android", lastName = ""),
)


val proposals = listOf(
    ProposalInListDto(id = "0", "Очень крутое предложение конфетка", user = users.random(), false,11,123, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "1", "Очень крутое предложение конфетка", user = users.random(), false,11,1, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "2", "Очень крутое предложение конфетка", user = users.random(), false,11,2, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "3", "Очень крутое предложение конфетка", user = users.random(), false,11,3, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "4", "Очень крутое предложение конфетка", user = users.random(),null, 11,0, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "5", "Очень крутое предложение конфетка", user = users.random(), null,11,123, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "6", "Очень крутое предложение конфетка", user = users.random(), true,0,0, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "7", "Очень крутое предложение конфетка", user = users.random(), null,11,0, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "8", "Очень крутое предложение конфетка", user = users.random(), true,11,123, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
    ProposalInListDto(id = "9", "Очень крутое предложение конфетка", user = users.random(), false,11,123, createdAt = 1713645386, proposalStatus = ProposalStatus.values().random() ),
)