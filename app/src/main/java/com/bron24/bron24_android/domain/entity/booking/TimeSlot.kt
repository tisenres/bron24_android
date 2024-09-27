package com.bron24.bron24_android.domain.entity.booking

data class TimeSlot(
    val startTime: String,
    val endTime: String,
    val isAvailable: Boolean = true,
    val isSelected: Boolean = false
)
