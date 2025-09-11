package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.model.Record
import kotlinx.coroutines.flow.Flow

// TODO: Removed this after review (all usage have been removed)
interface ClassDetailsRepository {
    /**
     * Observes the details of a specific class from an in-memory cache.
     * The cache for this classId is updated by calling [refreshClassDetails].
     *
     * @param classId The ID of the class to observe.
     * @return A Flow emitting the Class details, or null if not found/cached.
     */
    fun observeClassDetails(classId: String): Flow<Class?>

    /**
     * Fetches the latest details for a specific class from the API
     * and updates the in-memory cache for that classId.
     *
     * @param classId The ID of the class to refresh.
     */
    suspend fun refreshClassDetails(classId: String)

    /**
     * Observes the attendance records for a specific class and student
     * from an in-memory cache.
     * The cache for this classId-studentId combination is updated by calling [refreshClassRecords].
     *
     * @param classId The ID of the class.
     * @param studentId The ID of the student whose records are to be observed.
     * @return A Flow emitting the list of Records.
     */
    fun observeClassRecords(classId: String, studentId: String): Flow<List<Record>>

    /**
     * Fetches the latest attendance records for a specific class and student
     * from the API and updates the in-memory cache.
     *
     * @param classId The ID of the class.
     * @param studentId The ID of the student whose records are to be refreshed.
     */
    suspend fun refreshClassRecords(classId: String, studentId: String)

    // Currently, use /api/v1/student/{class_id}/all/attendance_records and then filter by studentId
    suspend fun getRecord(classId: String, studentId: String): List<Record>
}