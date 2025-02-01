package com.bron24.bron24_android.screens.menu_pages.screen_menu

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import javax.inject.Inject

class MenuScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):MenuScreenContract.Direction {
    override suspend fun moveToPhone() {
        appNavigator.replaceAll(PhoneNumberScreen())
    }
}