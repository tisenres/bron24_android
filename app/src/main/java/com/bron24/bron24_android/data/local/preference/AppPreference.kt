package com.bron24.bron24_android.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.user.User
import com.google.gson.Gson
import timber.log.Timber

private const val SELECTED_LANGUAGE = "selected_language"
private const val TOKEN_KEY = "auth_token"
private const val REFRESH_TOKEN_KEY = "refresh_token"

class AppPreference(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun getSelectedLanguage(): String? {
        return preferences.getString(SELECTED_LANGUAGE, null)
    }

    fun setSelectedLanguage(languageCode: String) {
        preferences.edit().putString(SELECTED_LANGUAGE, languageCode).apply()
    }

    fun isOnboardingCompleted(screenName: String): Boolean {
        return preferences.getBoolean(screenName, false)
    }

    fun setOnboardingCompleted(screenName: String, completed: Boolean) {
        preferences.edit().putBoolean(screenName, completed).apply()
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        preferences.edit()
            .putString(TOKEN_KEY, accessToken)
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .apply()
    }

    fun getAccessToken(): String? {
        // TODO: test token
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzM1MjA2ODQwLCJpYXQiOjE3MzI2MTQ4NDAsImp0aSI6IjgyMTY5ODVkNmVhZjRkZjQ5ZDhmYjhhZDllNTMwOWY4IiwidXNlcl9pZCI6M30.Vrgi8_JVToR9uqnl6RuNJJmsmYZs2ReKTe7igx69NHs"
//        return preferences.getString(TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return preferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun clearTokens() {
        preferences.edit()
            .remove(TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .apply()
    }

    fun saveUserData(phoneNumber: String, firstName: String, lastName: String) {
        Timber.tag("UserRepositoryImpl").d("first_name: %s", firstName)

        preferences.edit()
            .putString("phone_number", phoneNumber)
            .putString("first_name", firstName)
            .putString("last_name", lastName)
            .apply()
    }

    fun getUserPhoneNumber(): String {
        preferences.getString("phone_number", "")?.let {
            return it
        } ?: run {
            return ""
        }
    }

    fun getPersonalUserData(): User {
        Timber.tag("UserRepositoryImpl")
            .d("first_name: %s", preferences.getString("first_name", ""))
        return User(
            preferences.getString("first_name", "") ?: "",
            preferences.getString("last_name", "") ?: "",
            preferences.getString("phone_number", "") ?: ""
        )
    }

    fun saveBooking(booking: Booking) {
        val bookingJson = gson.toJson(booking)
        preferences
            .edit()
            .putString("booking", bookingJson)
            .apply()
    }

    fun getBooking(): Booking? {
        val bookingJson = preferences.getString("booking", null)
        return if (bookingJson != null) {
            gson.fromJson(bookingJson, Booking::class.java)
        } else {
            null
        }
    }
}