package com.bron24.bron24_android.common

data class FilterOptions(
    val selParking: Boolean = false,
    val selRoom: Boolean = false,
    val selShower: Boolean = false,
    val selOutdoor: Boolean = false,
    val selIndoor: Boolean = false,
    val startTime: String = "00:00",
    val endTime: String = "00:00",
    val minSumma: Int = 0,
    val maxSumma: Int = 1000000,
    val location: String = "",
    val selectedDate: String = ""
)

