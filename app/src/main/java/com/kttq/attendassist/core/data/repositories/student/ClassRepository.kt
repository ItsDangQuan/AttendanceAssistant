package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.data.network.responses.Class
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    /**
     * Observes the list of current classes for the student from an in-memory cache.
     * The cache is updated by calling [refreshCurrentClasses].
     */
    fun observeCurrentClasses(): Flow<List<Class>>

    /**
     * Fetches the latest current classes from the API and updates the in-memory cache.
     * Throws an exception on failure.
     */
    suspend fun refreshCurrentClasses()

    /**
     * Observes the list of past classes for the student from an in-memory cache.
     * The cache is updated by calling [refreshPastClasses].
     */
    fun observePastClasses(): Flow<List<Class>>

    /**
     * Fetches the latest past classes from the API and updates the in-memory cache.
     * Throws an exception on failure.
     */
    suspend fun refreshPastClasses()
}