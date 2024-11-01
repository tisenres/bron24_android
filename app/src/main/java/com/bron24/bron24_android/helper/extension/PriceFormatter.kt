package com.bron24.bron24_android.helper.extension

fun String.formatPrice(): String {
    return try {
        val floatPrice = this.toFloat()
        val intPrice = floatPrice.toInt()
        String.format("%,d", intPrice).replace(",", " ")
    } catch (e: NumberFormatException) {
        "0" // Return "0" if parsing fails
    }
}