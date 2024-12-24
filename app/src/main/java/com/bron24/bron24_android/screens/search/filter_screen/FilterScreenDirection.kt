package com.bron24.bron24_android.screens.search.filter_screen

import com.bron24.bron24_android.navigator.AppNavigator
import javax.inject.Inject

class FilterScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):FilterScreenContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }
}