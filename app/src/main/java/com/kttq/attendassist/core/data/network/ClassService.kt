package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import com.kttq.attendassist.core.data.network.dtos.RecordOut
import com.kttq.attendassist.core.data.network.dtos.SessionInfo
import com.kttq.attendassist.core.data.network.dtos.StudentBase
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ClassService {
    @GET("/api/v1/class/{class_id}/information")
    suspend fun getClassInformation(@Path("class_id") classId: String): Response<ClassInformation>

    @GET("/api/v1/class/{class_id}/students")
    suspend fun getClassStudents(@Path("class_id") classId: String): Response<List<StudentBase>>

    @GET("/api/v1/class/{class_id}/sessions")
    suspend fun getClassSessions(@Path("class_id") classId: String): Response<List<SessionInfo>>

    @GET("/api/v1/class/{class_id}/records")
    suspend fun getClassRecords(@Path("class_id") classId: String): Response<List<RecordOut>>
}