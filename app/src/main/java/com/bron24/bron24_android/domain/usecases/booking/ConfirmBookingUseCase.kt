package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfirmBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val preferencesRepository: PreferencesRepository
) {

   operator fun invoke(): Flow<Result<Unit>> = flow {
        val booking = preferencesRepository.getBooking()
        if (booking != null) {
            bookingRepository.confirmBooking(booking)
            emit(Result.success(Unit))
        }else{
            emit(Result.failure(Exception("help")))
        }
    }.catch { emit(Result.failure(it)) }
}