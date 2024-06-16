package com.bron24.bron24_android.app

import android.app.Application
import android.content.Context
import com.bron24.bron24_android.features.language.domain.LocaleManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setLocale(this)
    }

    override fun attachBaseContext(base: Context) {
        val sharedPreferences = base.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "uz") ?: "uz"
        super.attachBaseContext(LocaleManager.setLocale(base, languageCode))
    }

    private fun setLocale(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "uz") ?: "uz"
        LocaleManager.setLocale(context, languageCode)
    }
}
