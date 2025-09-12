package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.RecordOut

data class Record(
    val recordId: String,
    val studentId: String,
    val status: String,
    val sessionId: String
) {
    constructor(recordOut: RecordOut) : this(
        recordOut.recordId,
        recordOut.studentId,
        recordOut.status,
        recordOut.sessionId
    )
}
