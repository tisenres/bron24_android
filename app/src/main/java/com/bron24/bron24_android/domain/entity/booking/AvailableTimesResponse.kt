package com.bron24.bron24_android.domain.entity.booking

data class AvailableTimesResponse(
    val success: Boolean,
    val message: String,
    val availableTimeSlots: List<TimeSlot>,
)