package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.booking.Booking

interface BookingRepository {
    suspend fun createBooking(booking: Booking): Booking
    suspend fun getBookingById(bookingId: String): Booking
//    suspend fun cancelBooking(bookingId: String): Result<Unit>
}