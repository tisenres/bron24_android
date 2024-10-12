package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen

interface PreferencesRepository {
    fun isOnboardingCompleted(screen: OnboardingScreen): Boolean
    fun setOnboardingCompleted(screen: OnboardingScreen, completed: Boolean)
    fun saveUserData(phoneNumber: String, firstName: String, lastName: String)
    fun getUserPhoneNumber(): String
    fun saveBooking(booking: Booking)
    fun getBooking(): Booking?
}