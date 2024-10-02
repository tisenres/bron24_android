package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import javax.inject.Inject

class CreateBookingUseCase @Inject constructor(
    private val repository: BookingRepository
) {
    suspend fun execute(venueId: Int, date: String, sector: String, timeSlots: List<String>): Booking {
        return repository.createBooking(venueId, date, sector, timeSlots)
    }
}