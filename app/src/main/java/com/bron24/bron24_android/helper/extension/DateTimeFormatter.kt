package com.bron24.bron24_android.helper.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter as JavaDateTimeFormatter

object DateTimeFormatter {
    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun formatTimeRange(start: Long, end: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return "${sdf.format(Date(start))} - ${sdf.format(Date(end))}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatISODateTimeToHourString(isoDateTime: String): String {
        return try {
            val dateTime = OffsetDateTime.parse(isoDateTime)
            dateTime.format(JavaDateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            // If parsing fails, return the original string
            isoDateTime
        }
    }
}