package com.bron24.bron24_android.screens.auth.sms_otp

import android.util.Log
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import javax.inject.Inject

class OTPInputScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):OTPInputContract.Direction {
    override suspend fun moveToMenu() {
        appNavigator.replaceAll(MenuScreen())
    }

    override suspend fun moveToRegister() {
        Log.d("AAA", "moveToRegister: userInput")
    }

    override suspend fun back() {
        appNavigator.back()
    }
}