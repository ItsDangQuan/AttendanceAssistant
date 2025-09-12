package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.AttendanceRequest
import com.kttq.attendassist.core.data.network.dtos.EnrollmentCheckResponse
import com.kttq.attendassist.core.data.network.dtos.RecordCreate
import com.kttq.attendassist.core.data.network.dtos.RecordOut
import com.kttq.attendassist.core.data.network.dtos.StudentFull
import com.kttq.attendassist.core.data.network.dtos.StudentRecordOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentService {
    @GET("/api/v1/student")
    suspend fun getStudents(): Response<List<StudentRecordOut>>

    @GET("/api/v1/student/{student_id}/records")
    suspend fun getStudentRecords(@Path("student_id") studentId: String): Response<List<RecordOut>>

    @POST("/api/v1/student/record")
    suspend fun createStudentRecord(@Body recordCreate: RecordCreate): Response<RecordOut>

    @GET("/api/v1/student/current")
    suspend fun getCurrentStudent(): Response<StudentFull>

    @GET("/api/v1/student/all/attendance_records")
    suspend fun getAttendanceRecords(): Response<List<RecordOut>>

    @GET("/api/v1/student/check-enrollment/{class_id}")
    suspend fun checkEnrollment(@Path("class_id") classId: String): Response<EnrollmentCheckResponse>

    @GET("/api/v1/student/all/class/enroll")
    suspend fun getAllClassEnrollments(): Response<List<EnrollmentCheckResponse>>

    @GET("/api/v1/student/{class_id}/all/attendance_records")
    suspend fun getAllAttendanceRecords(@Path("class_id") classId: String): Response<List<RecordOut>>

    @POST("/api/v1/student/roll_call")
    suspend fun studentRollCall(@Body attendanceRequest: AttendanceRequest): Response<RecordOut>

    @GET("/api/v1/student/{student_id}/full")
    suspend fun getFullStudent(@Path("student_id") studentId: String): Response<StudentFull>
}