package com.bron24.bron24_android.screens.search.search_screen

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.navigator.AppNavigator
import javax.inject.Inject

class SearchScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):SearchScreenContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToInfo(venue: Venue) {

    }
}