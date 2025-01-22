//package com.bron24.bron24_android.helper.extension
//
//fun String.formatWithSpansPhoneNumber(): String {
//    val countryCode = "+998"
//    return if (this.length >= 12) {
//        val part1 = this.substring(3, 5)
//        val part2 = this.substring(5, 8)
//        val part3 = this.substring(8, 10)
//        val part4 = this.substring(10, 12)
//        "$countryCode $part1 $part2 $part3 $part4"
//    } else {
//        this
//    }
//}
//fun String.isValidUzbekPhoneNumber(): Boolean {
//    val regex = "^\\+998[0-9]{9}$".toRegex()
//    return regex.matches(this)
//}