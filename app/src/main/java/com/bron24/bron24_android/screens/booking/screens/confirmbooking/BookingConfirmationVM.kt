package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.usecases.booking.ConfirmBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import com.bron24.bron24_android.helper.util.isValidUzbekPhoneNumber
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BookingConfirmationVM @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val confirmBookingUseCase: ConfirmBookingUseCase,
    private val direction: BookingConfirmationContract.Direction,
    private val localStorage: LocalStorage
) : ViewModel(), BookingConfirmationContract.ViewModel {
    override fun onDispatchers(intent: BookingConfirmationContract.Intent): Job = intent {
        when (intent) {
            BookingConfirmationContract.Intent.Back -> {
                direction.back()
            }

            BookingConfirmationContract.Intent.Confirm -> {
                confirmBookingUseCase.invoke(
                    state.venueOrderInfo?.copy(secondPhone = state.secondPhoneNumber) ?: VenueOrderInfo(
                        0, "", "", "",
                        emptyList()
                    ).copy(secondPhone = state.secondPhoneNumber)
                ).onEach {
                    it.onSuccess {
                        direction.moveToNext(it)
                    }.onFailure {
                        postSideEffect(it.message?:"")
                    }
                }.launchIn(viewModelScope)
            }

            is BookingConfirmationContract.Intent.UpdatePhone -> {
                if(intent.phone.isValidUzbekPhoneNumber()){
                    reduce { state.copy(secondPhoneNumber = intent.phone) }
                }
            }
        }
    }
    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(BookingConfirmationContract.SideEffect(message))
        }
    }

    override fun initData(venueOrderInfo: VenueOrderInfo): Job = intent {
        createBookingUseCase.invoke(venueOrderInfo).onStart {
            reduce { state.copy(isLoading = true, venueOrderInfo = venueOrderInfo) }
        }.onEach {
            reduce { state.copy(isLoading = false, booking = it) }
        }.launchIn(viewModelScope)
    }

    override val container =
        container<BookingConfirmationContract.UIState, BookingConfirmationContract.SideEffect>(
            BookingConfirmationContract.UIState()
        )

}
