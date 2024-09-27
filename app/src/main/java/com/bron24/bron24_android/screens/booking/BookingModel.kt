package com.bron24.bron24_android.screens.booking

import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.usecases.booking.GetAvailableTimeSlotsUseCase
import javax.inject.Inject

class BookingModel @Inject constructor(
    private val getAvailableTimeSlotsUseCase: GetAvailableTimeSlotsUseCase
) {
    suspend fun getAvailableTimeSlots(venueId: Int, date: String, sector: String): List<TimeSlot> {
        return getAvailableTimeSlotsUseCase.execute(venueId, date, sector)
    }
}