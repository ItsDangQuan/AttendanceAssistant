package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.Session
import kotlinx.coroutines.flow.Flow

// If possible, else ignore. Temporary use Session as placeholder.
interface ScheduleRepository {
    /**
     * Observes the list of incoming classes for the student from an in-memory cache.
     * Cache updated by [refreshIncomingClasses].
     */
    fun observeIncomingClasses(): Flow<List<Session>>

    /**
     * Fetches the latest incoming classes from the API and updates the in-memory cache.
     */
    suspend fun refreshIncomingClasses()

    /**
     * Observes the list of today's classes for the student from an in-memory cache.
     * Cache updated by [refreshTodayClasses].
     */
    fun observeTodayClasses(): Flow<List<Session>>

    /**
     * Fetches the latest today's classes from the API and updates the in-memory cache.
     */
    suspend fun refreshTodayClasses()
}