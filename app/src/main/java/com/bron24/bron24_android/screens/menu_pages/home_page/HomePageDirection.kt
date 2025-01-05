package com.bron24.bron24_android.screens.menu_pages.home_page

import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.search.filter_screen.FilterScreen
import com.bron24.bron24_android.screens.search.search_screen.SearchScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import javax.inject.Inject

class HomePageDirection @Inject constructor(
    private val appNavigator: AppNavigator
):HomePageContract.Direction {
    override suspend fun moveToSearch() {
        appNavigator.push(SearchScreen())
    }

    override suspend fun moveToFilter(block: (FilterOptions) -> Unit) {
        appNavigator.push(FilterScreen(block))
    }

    override suspend fun moveToDetails(venueId: Int) {
        appNavigator.push(VenueDetailsScreen(venueId))
    }
}