package com.bron24.bron24_android.app

import android.app.Application
import android.content.Context
import com.bron24.bron24_android.helper.util.LocaleManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Bron24Application : Application() {

//    override fun attachBaseContext(base: Context) {
//        val sharedPreferences = base.getSharedPreferences("settings", Context.MODE_PRIVATE)
//        val languageCode = sharedPreferences.getString("selected_language", "uz") ?: "uz"
//        super.attachBaseContext(LocaleManager.setLocale(base, languageCode))
//    }
}
