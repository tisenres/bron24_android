package com.bron24.bron24_android.screens.venuedetails

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.BookingConfirmationScreen
import javax.inject.Inject

class VenueDetailsDirection @Inject constructor(
    private val appNavigator: AppNavigator
):VenueDetailsContract.Direction{
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToNext(info: VenueOrderInfo) {
        appNavigator.push(BookingConfirmationScreen(info))
    }
}