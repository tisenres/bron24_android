package com.bron24.bron24_android.screens.main

sealed class Screen(val route: String) {
    object LanguageSelection : Screen("languageSelection")
    object HowItWorksPager : Screen("howItWorksPager")
    object LocationPermission : Screen("locationPermission")
    object Main : Screen("main")
    object HomePage : Screen("homePage")
    object VenueDetails : Screen("venueDetails/{venueId}")
    object MapPage : Screen("mapPage")
    object CartPage : Screen("cartPage")
    object ProfilePage : Screen("profilePage")
}
