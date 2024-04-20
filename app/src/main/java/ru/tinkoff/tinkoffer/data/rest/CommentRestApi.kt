package ru.tinkoff.tinkoffer.data.rest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.tinkoff.tinkoffer.data.models.comments.request.CreateCommentDto
import ru.tinkoff.tinkoffer.data.models.comments.request.EditCommentDto

interface CommentRestApi {

    @PUT("comments/{commentId}")
    suspend fun editComment(
        @Path("commentId") commentId: String,
        @Body editCommentDto: EditCommentDto
    ): Response<ResponseBody>

    @DELETE("comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: String,
    ): Response<ResponseBody>

    @POST("comments/{commentId}/replies")
    suspend fun createCommentToComment(
        @Path("commentId") commentId: String,
        @Body createCommentDto: CreateCommentDto
    ): Response<ResponseBody>

    @POST("comments/proposals/{proposalId}")
    suspend fun createCommentToProposal(
        @Path("proposalId") proposalId: String,
        @Body createCommentDto: CreateCommentDto
    ): Response<ResponseBody>


}