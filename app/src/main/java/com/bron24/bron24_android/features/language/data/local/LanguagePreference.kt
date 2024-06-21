package com.bron24.bron24_android.features.language.data.local

import android.content.Context
import android.content.SharedPreferences

class LanguagePreference(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getSelectedLanguage(): String? {
        return preferences.getString("selected_language", "uz")
    }

    fun setSelectedLanguage(languageCode: String) {
        preferences.edit().putString("selected_language", languageCode).apply()
    }
}