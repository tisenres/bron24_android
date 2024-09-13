//package com.bron24.bron24_android.domain.usecases.booking
//
//import com.bron24.bron24_android.domain.repository.BookingRepository
//import javax.inject.Inject
//
//class CancelBookingUseCase @Inject constructor(private val repository: BookingRepository) {
//    suspend fun execute(bookingId: String): Result<Unit> =
//        repository.cancelBooking(bookingId)
//}