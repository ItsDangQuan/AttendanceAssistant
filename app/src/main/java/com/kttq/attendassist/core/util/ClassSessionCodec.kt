package com.kttq.attendassist.core.util

import java.nio.ByteBuffer
import java.util.UUID

object ClassSessionCodec {
    fun pack(classId: String, sessionId: UUID): ByteArray {
        val parts = classId.split("_")          // ["23TT2", "CS101"]
        val year = parts[0].take(2).toInt().toByte()
        val group = parts[0].drop(2)            // "TT2"

        val dept = parts[1].take(2)             // "CS"
        val number = parts[1].drop(2).toInt().toShort()

        val buffer = ByteBuffer.allocate(1 + 3 + 2 + 2 + 16)
        buffer.put(year)
        buffer.put(group.toByteArray(Charsets.US_ASCII)) // 3 bytes
        buffer.put(dept.toByteArray(Charsets.US_ASCII))  // 2 bytes
        buffer.putShort(number)

        // SessionId = UUIDv4 (128 bits)
        val bb = ByteBuffer.wrap(ByteArray(16))
        bb.putLong(sessionId.mostSignificantBits)
        bb.putLong(sessionId.leastSignificantBits)
        buffer.put(bb.array())

        return buffer.array()
    }

    fun unpack(bytes: ByteArray): Pair<String, UUID> {
        val buffer = ByteBuffer.wrap(bytes)

        val year = buffer.get().toInt() and 0xFF

        val groupBytes = ByteArray(3)
        buffer.get(groupBytes)
        val group = String(groupBytes, Charsets.US_ASCII)

        val deptBytes = ByteArray(2)
        buffer.get(deptBytes)
        val dept = String(deptBytes, Charsets.US_ASCII)

        val number = buffer.short.toInt() and 0xFFFF

        val msb = buffer.long
        val lsb = buffer.long
        val uuid = UUID(msb, lsb)

        val classId = "%02d%s_%s%d".format(year, group, dept, number)
        return classId to uuid
    }
}
