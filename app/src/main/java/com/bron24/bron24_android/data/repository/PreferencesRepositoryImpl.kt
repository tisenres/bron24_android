package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.domain.repository.PreferencesRepository
import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val appPreference: AppPreference
) : PreferencesRepository {

    override fun isOnboardingCompleted(screen: OnboardingScreen): Boolean {
        return appPreference.isOnboardingCompleted(screen.screenName)
    }

    override fun setOnboardingCompleted(screen: OnboardingScreen, completed: Boolean) {
        appPreference.setOnboardingCompleted(screen.screenName, completed)
    }

    override fun saveUserData(phoneNumber: String, firstName: String, lastName: String) {
        appPreference.saveUserData(phoneNumber, firstName, lastName)
    }

    override fun getUserPhoneNumber(): String {
        return appPreference.getUserPhoneNumber()
    }

    override fun saveBooking(booking: Booking) {
        appPreference.saveBooking(booking)
    }
}