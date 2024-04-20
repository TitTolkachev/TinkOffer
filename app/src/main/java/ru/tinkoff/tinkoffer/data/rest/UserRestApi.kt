package ru.tinkoff.tinkoffer.data.rest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.tinkoff.tinkoffer.data.models.users.requests.ChangeAvatarRequest
import ru.tinkoff.tinkoffer.data.models.users.requests.SingInRequest
import ru.tinkoff.tinkoffer.data.models.users.response.TokenResponse
import ru.tinkoff.tinkoffer.data.models.users.response.User

interface UserRestApi{
    @POST("users/sign-in")
    suspend fun singIn(@Body singInModel: SingInRequest): Response<TokenResponse>


    @POST("users/change-avatar")
    suspend fun changeAvatar(@Body changeAvatarModel: ChangeAvatarRequest): Response<Unit>


    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>


}
