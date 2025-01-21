package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val preferencesRepository: PreferencesRepository,
    private val localStorage: LocalStorage
) {
    operator fun invoke(
        info:VenueOrderInfo
    ): Flow<Booking> = flow {
        val currentBooking = bookingRepository.createBooking(info)
        //preferencesRepository.saveBooking(currentBooking)
        emit(currentBooking)
    }.catch { throw it }
}