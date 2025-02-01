package com.bron24.bron24_android.screens.orderdetails

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.BookingConfirmationScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import javax.inject.Inject

class OrderDetailsDirection @Inject constructor(
    private val appNavigator: AppNavigator
):OrderDetailsContact.Direction {
    override suspend fun moveToNext(info: VenueOrderInfo) {
        appNavigator.push(BookingConfirmationScreen(info))
    }

    override suspend fun back() {
        appNavigator.back()
    }
}