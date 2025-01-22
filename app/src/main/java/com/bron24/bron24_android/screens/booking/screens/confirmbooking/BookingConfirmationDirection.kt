package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import com.bron24.bron24_android.common.ScreenState
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.booking.screens.finishbooking.BookingSuccessScreen
import javax.inject.Inject

class BookingConfirmationDirection @Inject constructor(
    private val appNavigator: AppNavigator
):BookingConfirmationContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToNext(venueOrderInfo: VenueOrderInfo) {
        appNavigator.replaceAll(BookingSuccessScreen(venueOrderInfo))
    }
}