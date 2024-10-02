package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesRequestDto
import com.bron24.bron24_android.data.network.dto.booking.RequestBookingDto
import com.bron24.bron24_android.data.network.mappers.toDomain
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingApiService: BookingApiService
) : BookingRepository {

    override suspend fun getAvailableTimes(
        venueId: Int,
        date: String,
        sector: String
    ): AvailableTimesResponse {
        val request = AvailableTimesRequestDto(venueId, date, sector)
        val response = bookingApiService.getAvailableTimes(request)
        return response.toDomain()
    }

    override suspend fun createBooking(
        venueId: Int,
        date: String,
        sector: String,
        timeSlots: List<String>
    ): Booking {
        val request = RequestBookingDto(
            user = "998901881625",
            venueId = venueId,
            bookingDate = date,
            sector = sector,
            timeSlot = timeSlots
        )
        val response = bookingApiService.startBooking(request)
        return response.toDomain()
    }
}