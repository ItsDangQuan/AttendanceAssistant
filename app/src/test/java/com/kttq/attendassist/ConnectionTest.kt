package com.kttq.attendassist

import com.kttq.attendassist.core.data.network.AuthService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ConnectionTest {
    private val api = Retrofit.Builder()
        .baseUrl("http://0.0.0.0:8000") // emulator to localhost
        .build()
        .create(AuthService::class.java)

    @Test
    fun testConnection() = runBlocking {
        val response = api.logout()
        assertTrue(response.isSuccessful)
        println(response.raw())
    }
}

