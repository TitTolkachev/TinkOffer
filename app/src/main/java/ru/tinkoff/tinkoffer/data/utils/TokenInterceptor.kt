package ru.tinkoff.tinkoffer.data.utils

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore

class TokenInterceptor(
    private val dataStore: PrefsDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken =
            runBlocking {
                dataStore.tokenFlow.first()
            }
        val originalRequest = chain.request()
        val originalRequestWithAccessToken =
            originalRequest.newBuilder()
                .appendAccessToken(accessToken).build()

        return chain.proceed(originalRequestWithAccessToken)
    }

    private fun Request.Builder.appendAccessToken(token: String) = this.header("Authorization", "Bearer $token")
}