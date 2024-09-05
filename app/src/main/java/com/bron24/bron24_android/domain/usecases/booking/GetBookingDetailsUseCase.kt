package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import javax.inject.Inject

class GetBookingDetailsUseCase @Inject constructor(private val repository: BookingRepository) {
    suspend fun execute(bookingId: String): Booking =
        repository.getBookingById(bookingId)
}