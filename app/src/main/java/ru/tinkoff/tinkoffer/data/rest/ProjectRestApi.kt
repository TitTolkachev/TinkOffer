package ru.tinkoff.tinkoffer.data.rest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.tinkoff.tinkoffer.data.models.projects.request.CreateProjectDto
import ru.tinkoff.tinkoffer.data.models.projects.request.EditProjectDto
import ru.tinkoff.tinkoffer.data.models.projects.request.UserList
import ru.tinkoff.tinkoffer.data.models.projects.response.ActiveProjectDto
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInListDto
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInfoDto
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto

interface ProjectRestApi {
    @PUT("projects/{projectId}/remove-admin-role/{userId}")
    suspend fun removeAdminRole(
        @Path("projectId") projectId: String,
        @Path("userId") userId: String,
    ): ResponseBody

    @PUT("projects/{projectId}/assign-admin-role/{userId}")
    suspend fun addAdminRole(
        @Path("projectId") projectId: String,
        @Path("userId") userId: String,
    ): ResponseBody

    @GET("projects/{id}")
    suspend fun getProjectInfo(
        @Path("id") projectId: String
    ): Response<ProjectInfoDto>

    @PUT("projects/{id}")
    suspend fun editProject(
        @Path("id") projectId: String,
        @Body editModel: EditProjectDto
    ): Response<ProjectInfoDto>


    @DELETE("projects/{id}")
    suspend fun deleteProject(
        @Path("id") projectId: String,
    ): Response<ResponseBody>

    @GET("projects")
    suspend fun getProjectsByUser(): Response<List<ProjectInListDto>>

    @POST("projects")
    suspend fun createProject(
        @Body createProjectModel: CreateProjectDto
    ): Response<ProjectInfoDto>

    @POST("projects/{id}/add-participants")
    suspend fun addUserToProject(
        @Path("id") projectId: String,
        @Body listOfUsers: UserList
    ): Response<ProjectInfoDto>

    @POST("projects/{id}/activate")
    suspend fun activateProject(
        @Path("id") projectId: String,
    ): Response<ResponseBody>

    @GET("projects/{projectId}/proposals")
    suspend fun getProposalsByProject(
        @Path("projectId") projectId: String,
    ): Response<List<ProposalInListDto>>

    @GET("projects/{id}/active")
    suspend fun getActiveProject(
        @Path("id") projectId: String,
    ): Response<ActiveProjectDto>

    @DELETE("projects/{id}/users")
    suspend fun deleteUsersFromProject(
        @Path("id") projectId: String,
        @Body listOfUsers: List<String>
    ): Response<ResponseBody>

}