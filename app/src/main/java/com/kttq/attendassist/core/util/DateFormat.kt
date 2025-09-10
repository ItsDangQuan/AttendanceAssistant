package com.kttq.attendassist.core.util

import android.content.Context
import android.text.format.DateUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormatter @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun currentDate(): String {
        val today = LocalDate.now()
        val dateToFormat = Date.from(
            today.atStartOfDay(
                ZoneId.systemDefault()
            ).toInstant()
        )
        val formatFlags = DateUtils.FORMAT_SHOW_WEEKDAY or
                DateUtils.FORMAT_SHOW_DATE or
                DateUtils.FORMAT_NO_YEAR
        return DateUtils.formatDateTime(
            context,
            dateToFormat.time,
            formatFlags
        )
    }
}