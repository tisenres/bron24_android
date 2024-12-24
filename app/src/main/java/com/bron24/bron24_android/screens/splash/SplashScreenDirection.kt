package com.bron24.bron24_android.screens.splash

import com.bron24.bron24_android.common.ScreenState
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import com.bron24.bron24_android.screens.language.LanguageSelScreen
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import com.bron24.bron24_android.screens.on_board.OnBoardPager
import javax.inject.Inject

class SplashScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):SplashScreenContract.Direction {
    override suspend fun moveToNext(state: ScreenState) {
        when(state){
            ScreenState.LANGUAGE -> appNavigator.replace(LanguageSelScreen())
            ScreenState.MENU -> appNavigator.replace(MenuScreen())
            ScreenState.PHONE -> appNavigator.replace(PhoneNumberScreen())
            ScreenState.ON_BOARD -> appNavigator.replace(OnBoardPager())
        }
    }
}