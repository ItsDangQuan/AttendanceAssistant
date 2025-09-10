package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.Department
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DepartmentService {
    @GET("/api/v1/department")
    suspend fun getDepartments(): Response<Department>

    @POST("/api/v1/department")
    suspend fun createDepartment(@Body department: Department): Response<Department>

    @GET("/api/v1/department/{department_id}")
    suspend fun getDepartment(@Path("department_id") departmentId: String): Response<Department>
}