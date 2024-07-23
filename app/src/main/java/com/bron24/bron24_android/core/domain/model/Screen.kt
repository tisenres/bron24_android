package com.bron24.bron24_android.core.domain.model

sealed class Screen(val route: String) {
    object LanguageSelection : Screen("languageSelection")
    object CitySelection : Screen("citySelection")
    object HowItWorksPager : Screen("howItWorksPager")
    object HowItWorks1 : Screen("howItWorks1")
    object HowItWorks2 : Screen("howItWorks2")
    object LocationPermission : Screen("locationPermission")
    object Main : Screen("main")
    object HomePage : Screen("homePage")
    object MapPage : Screen("mapPage")
    object CartPage : Screen("cartPage")
    object ProfilePage : Screen("profilePage")
}
