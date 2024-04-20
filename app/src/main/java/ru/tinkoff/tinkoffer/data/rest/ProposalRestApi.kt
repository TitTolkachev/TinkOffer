package ru.tinkoff.tinkoffer.data.rest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.tinkoff.tinkoffer.data.models.proposals.request.ChangeProposalStatus
import ru.tinkoff.tinkoffer.data.models.proposals.request.CreateProposalRequest
import ru.tinkoff.tinkoffer.data.models.proposals.request.EditProposalDto
import ru.tinkoff.tinkoffer.data.models.proposals.request.VoteRequest
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalDto
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInfoDto

interface ProposalRestApi {

    @PUT("proposals/{proposalId}/status")
    suspend fun changeProposalStatus(
        @Path("proposalId") proposalId: String,
        @Body changeProposalStatusModel: ChangeProposalStatus
    ): Response<ResponseBody>


    @GET("proposals/{id}")
    suspend fun getProposalById(
        @Path("id") proposalId: String,
    ): Response<ProposalInfoDto>

    @PUT("proposals/{id}")
    suspend fun editProposal(
        @Path("id") proposalId: String,
        @Body editProposalModel: EditProposalDto
    ): Response<ProposalInfoDto>


    @POST("proposals")
    suspend fun createProposal(@Body createProposalRequest: CreateProposalRequest): Response<ProposalDto>


    @POST("proposals/{proposalId}/vote")
    suspend fun voteForProposal(
        @Path("proposalId") proposalId: String,
        @Body voteModel: VoteRequest
    ): Response<ResponseBody>


    @POST("proposals/{proposalId}/vote/cancel-vote")
    suspend fun cancelVoteForProposal(@Path("proposalId") proposalId: String): Response<ResponseBody>

//    @GET("proposals/projects/{projectId}")
//    suspend fun getProposalsByProject(@Path("projectId") projectId: String): Response<List<ProposalInListDto>>

}