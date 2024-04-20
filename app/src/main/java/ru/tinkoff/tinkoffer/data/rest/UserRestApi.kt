package ru.tinkoff.tinkoffer.data.rest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.tinkoff.tinkoffer.data.models.users.requests.ChangeAvatarRequest
import ru.tinkoff.tinkoffer.data.models.users.requests.SingInRequest
import ru.tinkoff.tinkoffer.data.models.users.response.TokenResponse
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto

interface UserRestApi{
    @POST("users/sign-in")
    suspend fun singIn(@Body singInModel: SingInRequest): Response<TokenResponse>


    @POST("users/logout")
    suspend fun logout(): Response<ResponseBody>


    @POST("users/change-avatar")
    suspend fun changeAvatar(@Body changeAvatarModel: ChangeAvatarRequest): Response<ResponseBody>


    @GET("users")
    suspend fun getAllUsers(): Response<List<UserInfoDto>>

    @GET("users/user-info")
    suspend fun getMyUserInfo(): Response<UserInfoDto>

    @GET("users/not-in-project/{projectId}")
    suspend fun getUserNotFromSelectedProject(@Path("projectId") projectId: String): Response<List<UserInfoDto>>

}
