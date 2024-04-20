package ru.tinkoff.tinkoffer.data.models.proposals.response

import kotlinx.serialization.Serializable
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto


@Serializable
data class CommentDto(
    val id: String,
    val text: String,
    val user: UserInfoDto,
    val proposalId: String,
    val parentCommentId: String,
    val createdAt: String,
)
