package com.bron24.bron24_android.helper.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeFormatter {
    private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun formatDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    fun formatTimeRange(start: Long, end: Long): String =
        "${timeFormat.format(Date(start))} - ${timeFormat.format(Date(end))}"

    fun formatISODateTimeToHourString(isoDateTime: String): String = try {
        // List of possible ISO date formats
        val isoFormats = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        )

        var parsedDate: Date? = null

        // Try parsing the date string with each format
        for (format in isoFormats) {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC") // Set UTC time zone
            try {
                parsedDate = sdf.parse(isoDateTime)
                if (parsedDate != null) {
                    break
                }
            } catch (e: ParseException) {
                // Continue trying with the next format
            }
        }

        if (parsedDate != null) {
            timeFormat.format(parsedDate)
        } else {
            isoDateTime // Return the original string if parsing fails
        }
    } catch (e: Exception) {
        isoDateTime // Return the original string in case of any exception
    }
}