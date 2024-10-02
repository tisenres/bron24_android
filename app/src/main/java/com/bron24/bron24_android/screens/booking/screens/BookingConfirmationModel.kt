package com.bron24.bron24_android.screens.booking.screens

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import javax.inject.Inject

class BookingConfirmationModel @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase
) {
    suspend fun fetchBookingDetails(venueId: Int, date: String, sector: String, timeSlots: List<TimeSlot>): Booking {
        return createBookingUseCase.execute(venueId, date, sector, timeSlots)
    }
}