package com.bron24.bron24_android.screens.booking.screens.finishbooking

import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.screens.booking.states.BookingSuccessInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookingSuccessViewModel @Inject constructor(

) : ViewModel() {

    private val _bookingInfo = MutableStateFlow<BookingSuccessInfo?>(null)
    val bookingInfo: StateFlow<BookingSuccessInfo?> get() = _bookingInfo.asStateFlow()

    fun initBookingInfo(
        orderId: String,
        venueName: String,
        date: String,
        sector: String,
        timeSlots: List<TimeSlot>
    ) {
        _bookingInfo.value = BookingSuccessInfo(
            orderId = orderId,
            venueName = venueName,
            date = date,
            sector = sector,
            timeSlots = timeSlots
        )
    }

}