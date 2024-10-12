package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import javax.inject.Inject

class ConfirmBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend fun execute(): Boolean {
        val booking = preferencesRepository.getBooking()
        return if (booking != null) {
            bookingRepository.confirmBooking(booking)
        } else {
            false
        }
    }
}