package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.usecases.booking.ConfirmBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

class BookingConfirmationVM @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val confirmBookingUseCase: ConfirmBookingUseCase,
): ViewModel(),BookingConfirmationContract.ViewModel {
    override fun onDispatchers(intent: BookingConfirmationContract.Intent): Job = intent {
        when(intent){
            BookingConfirmationContract.Intent.Back -> {}
            BookingConfirmationContract.Intent.Confirm -> {}
            is BookingConfirmationContract.Intent.UpdatePhone -> {}
        }
    }

    override fun initData(venueOrderInfo: VenueOrderInfo): Job = intent {
        createBookingUseCase.invoke(venueOrderInfo).onStart {
            reduce { state.copy(isLoading = true) }
        }.onEach {
            reduce { state.copy(isLoading = false, booking = it) }
        }.launchIn(viewModelScope)
    }

    override val container = container<BookingConfirmationContract.UIState, BookingConfirmationContract.SideEffect>(BookingConfirmationContract.UIState())

}

//class BookingConfirmationModel @Inject constructor(
//    private val createBookingUseCase: CreateBookingUseCase,
//    private val confirmBookingUseCase: ConfirmBookingUseCase,
//) {
//    suspend fun fetchBookingDetails(
//        venueId: Int,
//        date: String,
//        sector: String,
//        timeSlots: List<TimeSlot>
//    ): Booking {
//        val booking = createBookingUseCase.execute(venueId, date, sector, timeSlots)
//        return booking
//    }
//
//    suspend fun confirmBooking(): Boolean {
//        return confirmBookingUseCase.execute()
//    }
//}