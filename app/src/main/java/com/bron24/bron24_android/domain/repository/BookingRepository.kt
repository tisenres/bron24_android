package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking

interface BookingRepository {
    suspend fun getAvailableTimes(venueId: Int, date: String, sector: String): AvailableTimesResponse
    suspend fun createBooking(info:VenueOrderInfo): Booking
    suspend fun confirmBooking(info: VenueOrderInfo): Boolean
}