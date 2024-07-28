package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.user.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository

class GetUserBookingsUseCase(private val bookingRepository: BookingRepository) {

    fun execute(userId: String): List<Booking> {
        return bookingRepository.getUserBookings(userId)
    }
}