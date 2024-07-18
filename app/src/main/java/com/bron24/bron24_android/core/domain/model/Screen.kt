package com.bron24.bron24_android.core.domain.model

sealed class Screen(val route: String) {
    data object LanguageSelection : Screen("languageSelection")
    data object CitySelection : Screen("citySelection")
    data object HomePage : Screen("homePage")
    data object CartPage : Screen("cartPage")
    data object ProfilePage : Screen("profilePage")
    data object Main : Screen("main")
}
