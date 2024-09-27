package com.bron24.bron24_android.screens.booking

import com.bron24.bron24_android.domain.entity.booking.Booking

sealed class BookingState {
    object Idle : BookingState()
    object Loading : BookingState()
    object Success : BookingState()
    object Cancelled : BookingState()
    data class Error(val message: String) : BookingState()
}
