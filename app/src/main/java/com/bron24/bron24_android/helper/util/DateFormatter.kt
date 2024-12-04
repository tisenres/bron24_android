package com.bron24.bron24_android.helper.util

fun formatTime(sliderValue: Float): String {
    val totalMinutes = (sliderValue * 1440).toInt() // 1440 = 24 * 60
    val adjustedMinutes = (totalMinutes / 30) * 30 //30 minutlik vaqt intervali
    val hours = adjustedMinutes / 60
    val minutes = adjustedMinutes % 60
    return String.format("%02d:%02d", hours, minutes)
}

fun formatMoney(sliderValue: Float): String {
    val realValue = (sliderValue * 1_000_000).toInt()
    val adjustedValue = (realValue / 10_000) * 10_000
    return String.format("%,d", adjustedValue)
}

fun formatDate(millis: Long): String {
    val date = java.util.Date(millis)
    val formatter = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault())
    return formatter.format(date)
}

fun String.stringToInt(): Int {
    if(isEmpty()) return 0
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