package com.kttq.attendassist.core.util

import java.nio.ByteBuffer
import java.nio.ByteOrder

object ClassSessionCodec {
    fun pack(classId: Int, sessionId: Int): ByteArray {
        return ByteBuffer.allocate(8)
            .order(ByteOrder.LITTLE_ENDIAN)
            .putInt(classId)
            .putInt(sessionId)
            .array()
    }

    fun unpack(array: ByteArray): Pair<Int, Int> {
        val buffer = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN)
        return buffer.int to buffer.int
    }
}
