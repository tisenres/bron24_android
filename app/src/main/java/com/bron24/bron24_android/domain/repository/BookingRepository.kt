package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse

interface BookingRepository {
    suspend fun getAvailableTimes(venueId: Int, date: String, sector: String): AvailableTimesResponse
}