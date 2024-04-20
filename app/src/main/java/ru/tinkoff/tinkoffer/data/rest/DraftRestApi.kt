package ru.tinkoff.tinkoffer.data.rest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.tinkoff.tinkoffer.data.models.drafts.request.CreateCommentDraftDto
import ru.tinkoff.tinkoffer.data.models.drafts.request.CreateDraftDto
import ru.tinkoff.tinkoffer.data.models.drafts.request.CreateProposalFromDraftDto
import ru.tinkoff.tinkoffer.data.models.drafts.request.UpdateDraftDto
import ru.tinkoff.tinkoffer.data.models.drafts.response.DraftDto

interface DraftRestApi {
    @PUT("drafts/{draftId}")
    suspend fun editDraft(
        @Path("draftId") draftId: String,
        @Body updateDraftModel: UpdateDraftDto
    ): Response<ResponseBody>



    @DELETE("drafts/{draftId}")
    suspend fun deleteDraft(
        @Path("draftId") draftId: String,
    ): Response<ResponseBody>

    @POST("drafts/proposal")
    suspend fun createProposalDraft(
        @Body createDraftModel: CreateDraftDto
    ): Response<ResponseBody>


    @POST("drafts/proposal-from-draft")
    suspend fun createProposalFromDraft(
        @Body createProposalModel: CreateProposalFromDraftDto
    ): Response<ResponseBody>


    @POST("drafts/comment")
    suspend fun createCommentDraft(
        @Body createDraftModel: CreateCommentDraftDto
    ): Response<ResponseBody>

    @GET("drafts/proposals")
    suspend fun getProposalDrafts(
    ): Response<List<DraftDto>>

}