package ru.tinkoff.tinkoffer.data.utils

import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent
import retrofit2.Response

data class ErrorMessage(
    val messages: List<String>
)

fun <T> Response<T>.toErrorMessage() {
    val gson: Gson by KoinJavaComponent.inject(Gson::class.java)
    val itemType = object : TypeToken<ErrorMessage>() {}.type
    Log.d("SignInViewModel", this.body().toString())
    Log.d("SignInViewModel", this.errorBody().toString())

    val itemList = gson.fromJson<ErrorMessage>(this.body().toString(), itemType)
    Log.d("TEST", itemList.messages.toString())
}