package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import javax.inject.Inject

class CreateBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun execute(venueId: Int, date: String, sector: String, timeSlots: List<TimeSlot>): Booking {
        val phoneNumber = preferencesRepository.getUserPhoneNumber()

        val booking = Booking(
            orderId = 0,
            phoneNumber = phoneNumber,
            venueId = venueId,
            bookingDate = date,
            sector = sector,
            timeSlots = timeSlots,
        )
        try {
            val currentBooking = bookingRepository.createBooking(booking)
            preferencesRepository.saveBooking(currentBooking)
            return currentBooking
        } catch (e: Exception) {
            throw e
        }
    }
}