package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking

interface BookingRepository {
    suspend fun getAvailableTimes(venueId: Int, date: String, sector: String): AvailableTimesResponse
    suspend fun createBooking(venueId: Int, date: String, sector: String, timeSlots: List<String>): Booking
}