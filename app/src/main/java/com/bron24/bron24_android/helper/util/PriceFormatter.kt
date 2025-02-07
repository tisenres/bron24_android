package com.bron24.bron24_android.helper.util

fun String.formatPrice(): String {
    return try {
        val floatPrice = this.toFloat()
        val intPrice = floatPrice.toInt()
        String.format("%,d", intPrice).replace(",", " ")
    } catch (e: NumberFormatException) {
        "0"
    }
}

fun String.priceToInt(): Int {
    return this.replace("\\s".toRegex(), "").toInt()
}