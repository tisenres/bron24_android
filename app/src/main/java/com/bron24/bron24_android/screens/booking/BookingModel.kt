package com.bron24.bron24_android.screens.booking

import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.GetBookingDetailsUseCase
import javax.inject.Inject

class BookingModel @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val getBookingDetailsUseCase: GetBookingDetailsUseCase
) {
//    suspend fun createBooking(booking: Booking): Booking {
//        return createBookingUseCase.execute(booking)
//    }

//    suspend fun getBookingDetails(bookingId: String): Booking {
//        return getBookingDetailsUseCase.execute(bookingId)
//    }
}