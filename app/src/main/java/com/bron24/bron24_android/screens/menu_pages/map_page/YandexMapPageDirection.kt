package com.bron24.bron24_android.screens.menu_pages.map_page

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.BookingConfirmationScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import javax.inject.Inject

class YandexMapPageDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : YandexMapPageContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToBooking(info: VenueOrderInfo) {
        appNavigator.push(BookingConfirmationScreen(info))
    }
}