package com.bron24.bron24_android.core.domain.model

sealed class Screen(val route: String) {
    object LanguageSelection : Screen("languageSelection")
    object CitySelection : Screen("citySelection")
    object Main : Screen("main")
    object HomePage : Screen("homePage")
    object CartPage : Screen("cartPage")
    object ProfilePage : Screen("profilePage")
}
