package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesRequestDto
import com.bron24.bron24_android.data.network.dto.booking.RequestBookingDto
import com.bron24.bron24_android.data.network.mappers.toDomain
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.helper.util.formatPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingApiService: BookingApiService,
    private val localStorage: LocalStorage
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
        info: VenueOrderInfo
    ): Booking {

        val formattedTimeSlots: List<String> = info.timeSlots.map { timeSlot ->
            "${timeSlot.startTime}-${timeSlot.endTime}"
        }
        val booking = Booking(
            orderId = 0,
            phoneNumber = localStorage.phoneNumber,
            firstName = "${localStorage.firstName} ${localStorage.lastName}",
            venueId = info.venueId,
            bookingDate = info.date,
            sector = info.sector,
            timeSlots = info.timeSlots,
        )

        val bookingRequest = RequestBookingDto(
            venueId = info.venueId,
            bookingDate = info.date,
            sector = info.sector,
            timeSlot = formattedTimeSlots
        )
        val response = bookingApiService.startBooking(bookingRequest)
        return booking.copy(
            venueName = response.data.venue?.venueName,
            venueAddress = response.data.venue?.venueAddress,
            totalHours = response.data.hours ?: 0.0,
            cost = response.data.cost.toString().formatPrice(),
            orderIds = response.data.orderIds,
        )
    }

    override suspend fun confirmBooking(info: VenueOrderInfo): VenueOrderInfo = withContext(Dispatchers.IO) {
        val response = bookingApiService.finishBooking(info.toNetworkModel())
        response.toDomain()
    }
}