package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.user.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository

class GetBookingDetailsUseCase(private val bookingRepository: BookingRepository) {

    fun execute(bookingId: String): Booking {
        return bookingRepository.getBookingById(bookingId)
    }
}