package com.kttq.attendassist

import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.injection.modules.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.http.GET
import okhttp3.ResponseBody
import javax.inject.Inject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotNull
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NetworkModuleHiltTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var tokenRepository: TokenRepository // injected FakeTokenRepository from test module

    // Simple interface used only in tests to call a protected endpoint
    interface TestApi {
        @GET("protected")
        fun getProtected(): Call<ResponseBody>
    }

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun interceptorAddsAccessTokenHeader() = runBlocking {
        // arrange: set token in fake repo
        tokenRepository.saveTokens("test-token-123", "refresh-abc")

        // Build baseRetrofit pointing to mock server
        val baseRetrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .build()

        val baseAuthService = baseRetrofit.create(AuthService::class.java)

        val authRetrofit = NetworkModule.provideAuthRetrofit(
            baseRetrofit,
            tokenRepository,
            object : dagger.Lazy<AuthService> { override fun get() = baseAuthService }
        )

        val testApi = authRetrofit.create(TestApi::class.java)

        // server will respond 200
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        // act
        val resp = testApi.getProtected().execute()

        // assert request header
        val recorded = mockWebServer.takeRequest()
        assertEquals("Bearer test-token-123", recorded.getHeader("Authorization"))

        // and the response should be successful
        assertTrue(resp.isSuccessful)
    }

    @Test
    fun authenticatorRefreshesTokenOn401AndRetries() = runBlocking {
        // Arrange: start with expired token
        tokenRepository.saveTokens("expired-token", "refresh-token-xyz")

        // Enqueue sequence:
        mockWebServer.enqueue(MockResponse().setResponseCode(401).setBody("unauthorized"))
        val refreshJson = """{"access_token":"new-token-456","refresh_token":"new-refresh-789"}"""
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(refreshJson))
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val baseRetrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val baseAuthService = baseRetrofit.create(AuthService::class.java)

        val authRetrofit = NetworkModule.provideAuthRetrofit(
            baseRetrofit,
            tokenRepository,
            object : dagger.Lazy<AuthService> { override fun get() = baseAuthService }
        )

        val testApi = authRetrofit.create(TestApi::class.java)

        // Act
        val resp = testApi.getProtected().execute()
        assertTrue(resp.isSuccessful)

        // Consume requests in order
        val original = mockWebServer.takeRequest()   // original -> 401
        val refreshCall = mockWebServer.takeRequest()// token refresh call by TokenAuthenticator
        val retried = mockWebServer.takeRequest()    // retried original request

        // The retried request should contain the NEW token
        assertEquals("Bearer new-token-456", retried.getHeader("Authorization"))

        // And tokenRepository should have been updated with the refreshed tokens
        // If you're using the FakeTokenRepository from examples, it exposes a helper to read the current token:
        if (tokenRepository is FakeTokenRepository) {
            val savedAccess = (tokenRepository as FakeTokenRepository).currentAccessToken()
            assertEquals("new-token-456", savedAccess)
        } else {
            // Fallback minimal assertion: Flow should not be null
            assertNotNull(tokenRepository.accessToken)
        }
    }
}

