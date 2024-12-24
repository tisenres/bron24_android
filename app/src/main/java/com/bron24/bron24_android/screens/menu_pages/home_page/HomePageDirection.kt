package com.bron24.bron24_android.screens.menu_pages.home_page

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.search.filter_screen.FilterScreen
import com.bron24.bron24_android.screens.search.search_screen.SearchScreen
import javax.inject.Inject

class HomePageDirection @Inject constructor(
    private val appNavigator: AppNavigator
):HomePageContract.Direction {
    override suspend fun moveToSearch() {
        appNavigator.push(SearchScreen())
    }

    override suspend fun moveToFilter() {
        appNavigator.push(FilterScreen())
    }
}