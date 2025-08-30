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

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authService: dagger.Lazy<AuthService>,
) : Authenticator {
    private val refreshTokenMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenInFailedRequest =
            response.request.header("Authorization")?.substringAfter("Bearer ")

        return runBlocking {
            val currentRefreshToken = tokenRepository.refreshToken.first()
            Log.d("Auth", "authenticate called. tokenInFailedRequest=$tokenInFailedRequest, resp.code=${response.code}")

            if (currentRefreshToken == null) {
                tokenRepository.clearTokens()
                return@runBlocking null
            }

            refreshTokenMutex.withLock {
                val latestAccessToken = tokenRepository.accessToken.first()
                Log.d("Auth","latestAccessToken=$latestAccessToken")
                if (latestAccessToken != null && latestAccessToken != tokenInFailedRequest) {
                    return@withLock response.request.newBuilder()
                        .header("Authorization", "Bearer $latestAccessToken")
                        .build()
                }

                try {
                    Log.d("Auth", "Calling authService.refresh(...) now")
                    val newTokensResponse =
                        authService.get().refresh(RefreshToken(currentRefreshToken))
                    Log.d("Auth","refresh response body: ${newTokensResponse.body()}")
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
                    Log.d("Auth", "Exception during token refresh")
                    tokenRepository.clearTokens()
                    null
                }
            }
        }
    }
}