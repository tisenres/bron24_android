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
    object BookingConfirmationScreen : Screen("bookingConfirmationScreen?venueId={venueId}&date={date}&sector={sector}&timeSlots={timeSlots}")
    object BookingSuccessScreen : Screen("bookingSuccessScreen?orderId={orderId}&venueName={venueName}&date={date}&sector={sector}&timeSlots={timeSlots}")
    object MapPage : Screen("mapPage?latitude={latitude}&longitude={longitude}&selectedVenueId={selectedVenueId}")
    object OrdersPage : Screen("ordersPage")
    object OrderDetails : Screen("orderDetails/{orderId}")
    object ProfilePage : Screen("profilePage")
}
