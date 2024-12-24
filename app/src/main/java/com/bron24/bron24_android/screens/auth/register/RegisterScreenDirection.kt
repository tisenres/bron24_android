package com.bron24.bron24_android.screens.auth.register

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import javax.inject.Inject

class RegisterScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):RegisterScreenContract.Direction {
    override suspend fun back() {
        appNavigator.popUntil(PhoneNumberScreen())
    }

    override suspend fun moveToNext() {
        appNavigator.replaceAll(MenuScreen())
    }
}