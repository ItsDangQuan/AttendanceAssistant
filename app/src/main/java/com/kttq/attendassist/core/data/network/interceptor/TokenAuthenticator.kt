package com.kttq.attendassist.core.data.network.interceptor

import android.util.Log
import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.dtos.RefreshToken
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Named

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    @param:Named("PublicAuthService") private val authService: AuthService,
) : Authenticator {
    private val refreshTokenMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenInFailedRequest =
            response.request.header("Authorization")?.substringAfter("Bearer ")

        return runBlocking {
            val currentRefreshToken = tokenRepository.refreshToken.first()

            if (currentRefreshToken == null) {
                tokenRepository.clearTokens()
                return@runBlocking null
            }

            refreshTokenMutex.withLock {
                val latestAccessToken = tokenRepository.accessToken.first()
                if (latestAccessToken != null && latestAccessToken != tokenInFailedRequest) {
                    return@withLock response.request.newBuilder()
                        .header("Authorization", "Bearer $latestAccessToken")
                        .build()
                }

                try {
                    val newTokensResponse =
                        authService.refresh(RefreshToken(currentRefreshToken))
                    if (!newTokensResponse.isSuccessful || newTokensResponse.body() == null) {
                        tokenRepository.clearTokens()
                        return@withLock null
                    }

                    val tokens = newTokensResponse.body()!!
                    tokenRepository.saveTokens(
                        tokens.accessToken,
                        tokens.refreshToken
                    )

                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokens.accessToken}")
                        .build()
                } catch (e: Exception) {
                    Log.e("Auth", "Error during token refresh", e)
                    tokenRepository.clearTokens()
                    null
                }
            }
        }
    }
}