package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import javax.inject.Inject

class CreateBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun execute(venueId: Int, date: String, sector: String, timeSlots: List<String>): Booking {
        val phoneNumber = preferencesRepository.getUserPhoneNumber()
        return bookingRepository.createBooking(phoneNumber, venueId, date, sector, timeSlots)
    }
}