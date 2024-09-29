package com.bron24.bron24_android.helper.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter as JavaDateTimeFormatter

object DateTimeFormatter {
    private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun formatDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    fun formatTimeRange(start: Long, end: Long): String =
        "${timeFormat.format(Date(start))} - ${timeFormat.format(Date(end))}"

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatISODateTimeToHourString(isoDateTime: String): String = try {
        OffsetDateTime.parse(isoDateTime).format(JavaDateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        isoDateTime
    }
}