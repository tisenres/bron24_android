package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.user.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository

class CreateBookingUseCase(private val bookingRepository: BookingRepository) {

    fun execute(booking: Booking): Booking {
        return bookingRepository.createBooking(booking)
    }
}