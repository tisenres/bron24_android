package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesRequestDto
import com.bron24.bron24_android.data.network.dto.booking.RequestBookingDto
import com.bron24.bron24_android.data.network.mappers.toDomain
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.helper.extension.formatPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingApiService: BookingApiService
) : BookingRepository {

    private lateinit var currentBooking: Booking

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
        booking: Booking
    ): Booking {

        val formattedTimeSlots: List<String> = booking.timeSlots.map { timeSlot ->
            "${timeSlot.startTime}-${timeSlot.endTime}"
        }

        val bookingRequest = RequestBookingDto(
            user = booking.phoneNumber,
            venueId = booking.venueId,
            bookingDate = booking.bookingDate,
            sector = booking.sector,
            timeSlot = formattedTimeSlots
        )
        currentBooking = booking
        val response = bookingApiService.startBooking(bookingRequest)

        currentBooking.apply {
            firstName = response.data.user?.firstName
            lastName = response.data.user?.lastName
            venueName = response.data.venue?.venueName
            venueAddress = response.data.venue?.venueAddress
            totalHours = response.data.hours
            cost = response.data.cost.toString().formatPrice()
            orderIds = response.data.orderIds
        }

        return currentBooking
    }

    override suspend fun confirmBooking(booking: Booking): Boolean = withContext(Dispatchers.IO) {
        val response = bookingApiService.finishBooking(booking.toNetworkModel())
        response.success
    }
}