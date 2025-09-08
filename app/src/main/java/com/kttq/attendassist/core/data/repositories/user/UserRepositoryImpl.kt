package com.kttq.attendassist.core.data.repositories.user

import com.kttq.attendassist.core.data.network.UserService
import com.kttq.attendassist.core.data.network.responses.UserOut
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Assuming a single instance of UserRepository throughout the app
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getCurrentUser(): UserOut? {
        return try {
            val response = userService.current()
            if (response.isSuccessful) {
                response.body()
            } else {
                // You might want to handle different HTTP error codes specifically
                // For now, returning null for any unsuccessful response
                println("Error fetching user: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            // Handle exceptions like network errors
            println("Exception fetching user: ${e.message}")
            null
        }
    }
}
