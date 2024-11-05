package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.repository.BookingRepository
import javax.inject.Inject

class GetAvailableTimeSlotsUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {
    suspend fun execute(venueId: Int, date: String, sector: String): List<TimeSlot> {
        val response = bookingRepository.getAvailableTimes(venueId, date, sector)

        if (!response.success) {
            return emptyList()
        }

        return response.availableTimeSlots
    }
}