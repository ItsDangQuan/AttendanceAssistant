package com.kttq.attendassist.core.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.text.format.DateUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    companion object {
        fun localTimeAsFormattedString(
            positiveMinuteOffset: Long = 0
        ): String {
            val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            return fmt.withZone(ZoneOffset.UTC)
                .format(Instant.now().plus(Duration.ofMinutes(positiveMinuteOffset)))
        }

        fun serverUtcStringToLocalDisplay(
            server: String,
            outputPattern: String = "HH:mm"
        ): String? {
            val parser = DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .optionalStart()
                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
                .optionalEnd()
                .toFormatter()

            return try {
                val ldt = LocalDateTime.parse(server, parser)
                val instant = ldt.toInstant(ZoneOffset.UTC)
                val zdtLocal = instant.atZone(ZoneId.systemDefault())
                zdtLocal.format(DateTimeFormatter.ofPattern(outputPattern))
            } catch (_: DateTimeParseException) {
                null
            }
        }
    }

    private val _formattedDate = MutableStateFlow(getCurrentDateAsString())
    val formattedDate: StateFlow<String> = _formattedDate

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        val intentFilter = IntentFilter(Intent.ACTION_DATE_CHANGED)
        val receiver = DateChangeReceiver { updateDate() }
        context.registerReceiver(receiver, intentFilter)
    }

    private fun updateDate() {
        scope.launch {
            _formattedDate.emit(getCurrentDateAsString())
        }
    }

    private fun getCurrentDateAsString(): String {
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

class DateChangeReceiver(
    private val onDateChanged: () -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == Intent.ACTION_DATE_CHANGED) {
            onDateChanged()
        }
    }
}