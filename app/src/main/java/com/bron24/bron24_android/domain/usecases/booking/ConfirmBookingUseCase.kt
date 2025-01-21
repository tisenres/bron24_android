package com.bron24.bron24_android.domain.usecases.booking

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.repository.BookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfirmBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
) {

   operator fun invoke(info: VenueOrderInfo): Flow<Result<Unit>> = flow {
       val check = bookingRepository.confirmBooking(info)
       if (check){
           emit(Result.success(Unit))
       }else emit(Result.failure(Exception("error")))
    }.catch { emit(Result.failure(it)) }
}