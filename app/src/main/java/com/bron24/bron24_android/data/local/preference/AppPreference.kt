package com.bron24.bron24_android.data.local.preference

import android.content.Context
import android.content.SharedPreferences

//private const val IS_ONBOARDING_COMPLETED = "is_onboarding_completed"
private const val SELECTED_LANGUAGE = "selected_language"
private const val TOKEN_KEY = "auth_token"
private const val REFRESH_TOKEN_KEY = "refresh_token"

class AppPreference(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

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
        return preferences.getString(TOKEN_KEY, null)
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
}