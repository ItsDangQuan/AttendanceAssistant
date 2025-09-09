package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.responses.UserOut
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    // Should we put the Body annotation here or in the data class?
    @GET("/api/v1/user/current")
    suspend fun current(): Response<UserOut>
}