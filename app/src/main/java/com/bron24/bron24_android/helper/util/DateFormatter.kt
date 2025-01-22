package com.bron24.bron24_android.helper.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

fun formatTime(sliderValue: Float): String {
    val totalMinutes = (sliderValue * 1440).toInt() // 1440 = 24 * 60
    val adjustedMinutes = (totalMinutes / 30) * 30 //30 minutlik vaqt intervali
    val hours = adjustedMinutes / 60
    val minutes = adjustedMinutes % 60
    return String.format("%02d:%02d", hours, minutes)
}

fun formatMoney(sliderValue: Float): Int {
    val realValue = (sliderValue * 1_000_000).toInt()
    val adjustedValue = (realValue / 50_000) * 50_000
    return adjustedValue
}

fun formatDate(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun String.stringToInt(): Int {
    if (isEmpty()) return 0
    return replace(" ", "").toInt()
}

fun String.formatPhoneNumber(): String {
    val countryCode = "+998"
    val part1 = substring(3, 5)
    val part2 = substring(5, 8)
    val part3 = substring(8, 10)
    val part4 = substring(10, 12)
    return "$countryCode $part1 $part2 $part3 $part4"
}

fun String.isValidUzbekPhoneNumber(): Boolean {
    val regex = "^\\+998[0-9]{9}$".toRegex()
    return regex.matches(this)
}

fun formatISODateTimeToHourString(isoDateTime: String): String = try {

    val isoFormats = arrayOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "yyyy-MM-dd'T'HH:mm:ssXXX",
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    )

    var parsedDate: Date? = null

    for (format in isoFormats) {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        try {
            parsedDate = sdf.parse(isoDateTime)
            if (parsedDate != null) {
                break
            }
        } catch (e: ParseException) {

        }
    }

    if (parsedDate != null) {
        timeFormat.format(parsedDate)
    } else {
        isoDateTime
    }
} catch (e: Exception) {
    isoDateTime
}