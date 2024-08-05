package com.bron24.bron24_android.data.local.preference

import android.content.Context
import android.content.SharedPreferences

private const val IS_ONBOARDING_COMPLETED = "is_onboarding_completed"
private const val SELECTED_LANGUAGE = "selected_language"
private const val TOKEN_KEY = "auth_token"


class AppPreference(private val context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getSelectedLanguage(): String? {
        return preferences.getString(SELECTED_LANGUAGE, null)
    }

    fun setSelectedLanguage(languageCode: String) {
        preferences.edit().putString(SELECTED_LANGUAGE, languageCode).apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return preferences.getBoolean(IS_ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        preferences.edit().putBoolean(IS_ONBOARDING_COMPLETED, completed).apply()
    }

    fun saveToken(token: String) {
        preferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        preferences.edit().remove(TOKEN_KEY).apply()
    }
}