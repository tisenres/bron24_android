package com.bron24.bron24_android.screens.menu_pages.map_page

import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.navigator.AppNavigator
import javax.inject.Inject

class YandexMapPageDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : YandexMapPageContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToInfo(venueDetails: VenueDetails) {
        //appNavigator.push
    }
}