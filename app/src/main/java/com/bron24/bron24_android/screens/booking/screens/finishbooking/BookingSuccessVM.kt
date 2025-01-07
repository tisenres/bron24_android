package com.bron24.bron24_android.screens.booking.screens.finishbooking

import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.common.VenueOrderInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BookingSuccessVM @Inject constructor(
    val direction: BookingSuccessContract.Direction
): ViewModel(),BookingSuccessContract.ViewModel {
    override fun onDispatchers(intent: BookingSuccessContract.Intent): Job = intent {
        when(intent){
            BookingSuccessContract.Intent.ClickMenu -> {
                direction.moveToMenu()
            }
            BookingSuccessContract.Intent.ClickOrder -> {
                direction.moveToOrder()
            }
        }
    }

    override val container = container<BookingSuccessContract.UIState, BookingSuccessContract.SideEffect>(BookingSuccessContract.UIState())
}