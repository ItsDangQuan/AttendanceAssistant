package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.RecordOut
import com.kttq.attendassist.core.data.network.dtos.StudentFull

data class StudentPerformance(
    // TODO: Consider remove this, there is no convenient API exposing this
    // val userId: String,
    val studentId: String,
    val studentName: String,

    val classId: String,

    val studentAttendance: List<Record>

    // This is beyond the scope of the Student table.
) {
    constructor(
        studentFull: StudentFull,
        classId: String,
        recordOutList: List<RecordOut>
    ) : this(
        studentFull.studentId,
        studentFull.lastName + studentFull.lastName,
        classId,
        recordOutList.map { Record(it) }
    )
}
