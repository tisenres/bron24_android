//package com.bron24.bron24_android.features.language.data.local
//
//import android.content.Context
//import android.content.SharedPreferences
//import com.bron24.bron24_android.helper.util.LocaleManager
//
//class LanguagePreference(private val context: Context) {
//    private val preferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
//
//    fun getSelectedLanguage(): String? {
//        return preferences.getString("selected_language", null)
//    }
//
//    fun setSelectedLanguage(languageCode: String) {
//        preferences.edit().putString("selected_language", languageCode).apply()
//    }
//}