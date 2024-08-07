package com.bron24.bron24_android.helper.extension

//fun String.toUzbekPhoneNumberInt(): Int {
//    return this.replace("+", "").toInt()
//}

fun String.formatWithSpansPhoneNumber(): String {
    val countryCode = "+998"
    val part1 = this.substring(4, 6)
    val part2 = this.substring(6, 9)
    val part3 = this.substring(9, 11)
    val part4 = this.substring(11, 13)
    return "$countryCode $part1 $part2 $part3 $part4"
}

fun Int.toUzbekPhoneNumberString(): String {
    return "+998" + this.toString().padStart(9, '0')
}
