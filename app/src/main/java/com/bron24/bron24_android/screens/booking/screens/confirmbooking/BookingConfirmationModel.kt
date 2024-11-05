package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.usecases.booking.ConfirmBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import javax.inject.Inject

class BookingConfirmationModel @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val confirmBookingUseCase: ConfirmBookingUseCase,
) {
    suspend fun fetchBookingDetails(
        venueId: Int,
        date: String,
        sector: String,
        timeSlots: List<TimeSlot>
    ): Booking {
        val booking = createBookingUseCase.execute(venueId, date, sector, timeSlots)
        return booking
    }

    suspend fun confirmBooking(): Boolean {
        return confirmBookingUseCase.execute()
    }
}