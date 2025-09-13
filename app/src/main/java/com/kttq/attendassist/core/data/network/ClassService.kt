package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.ClassCreate
import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import com.kttq.attendassist.core.data.network.dtos.RecordOut
import com.kttq.attendassist.core.data.network.dtos.SessionInfo
import com.kttq.attendassist.core.data.network.dtos.StudentBase
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClassService {
    @GET("/api/v1/class/{class_id}/information")
    suspend fun getClassInformation(@Path("class_id") classId: Int): Response<ClassInformation>

    @POST("/api/v1/class")
    suspend fun createClass(@Body classCreate: ClassCreate): Response<ClassCreate>

    @GET("/api/v1/class/{class_id}/students")
    suspend fun getClassStudents(@Path("class_id") classId: Int): Response<List<StudentBase>>

    @GET("/api/v1/class/{class_id}/sessions")
    suspend fun getClassSessions(@Path("class_id") classId: Int): Response<List<SessionInfo>>

    @GET("/api/v1/class/{class_id}/records")
    suspend fun getClassRecords(@Path("class_id") classId: Int): Response<List<RecordOut>>
}