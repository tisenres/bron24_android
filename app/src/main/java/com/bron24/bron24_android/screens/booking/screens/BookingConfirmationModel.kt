package com.bron24.bron24_android.screens.booking.screens

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.usecases.booking.ConfirmBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
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