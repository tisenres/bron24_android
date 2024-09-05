package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.data.network.mappers.toDto
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingApiService: BookingApiService
) : BookingRepository {
    override suspend fun createBooking(booking: Booking): Booking =
        bookingApiService.createBooking(booking.toDto()).toDomainModel()

    override suspend fun getBookingById(bookingId: String): Booking =
        bookingApiService.getBookingById(bookingId).toDomainModel()
}