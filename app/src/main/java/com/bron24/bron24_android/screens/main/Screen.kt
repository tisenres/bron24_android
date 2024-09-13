package com.bron24.bron24_android.screens.main

sealed class Screen(val route: String) {
    object LanguageSelection : Screen("languageSelection")
    object HowItWorksPager : Screen("howItWorksPager")
    object PhoneNumberInput : Screen("phone_number_input")
    object OTPInput : Screen("otp_input/{phoneNumber}")
    object UserDataInput : Screen("userInput")
    object LocationPermission : Screen("locationPermission")
    object Main : Screen("main")
    object HomePage : Screen("homePage")
    object Filter : Screen("filter")
    object VenueDetails : Screen("venueDetails/{venueId}")
//    object MapPage : Screen("mapPage/{venueId}")
    object Booking : Screen("booking/{venueId}")
    object MapPage : Screen("mapPage?latitude={latitude}&longitude={longitude}&selectedVenueId={selectedVenueId}")
    object OrdersPage : Screen("ordersPage")
    object ProfilePage : Screen("profilePage")
}
