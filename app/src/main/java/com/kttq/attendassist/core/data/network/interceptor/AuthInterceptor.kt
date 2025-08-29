package com.kttq.attendassist.core.data.network.interceptor

import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.accessToken.first()
        }
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader(
                name = "Authorization",
                value = "Bearer $it"
            )
        }
        return chain.proceed(
            request = requestBuilder.build()
        )
    }
}