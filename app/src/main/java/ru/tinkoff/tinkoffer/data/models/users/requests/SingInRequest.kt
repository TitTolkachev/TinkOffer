package ru.tinkoff.tinkoffer.data.models.users.requests

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SingInRequest(
    @SerializedName("temp_token") val tempToken: String
)
