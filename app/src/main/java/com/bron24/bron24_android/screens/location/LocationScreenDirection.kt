package com.bron24.bron24_android.screens.location

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import javax.inject.Inject

class LocationScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):LocationScreenContract.Direction{
    override suspend fun moveToNext() {
        appNavigator.replace(MenuScreen())
    }
}