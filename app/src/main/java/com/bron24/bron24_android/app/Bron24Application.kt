package com.bron24.bron24_android.app

import android.app.Application
import android.content.Context
import com.bron24.bron24_android.helper.util.LocaleManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.yandex.mapkit.MapKitFactory

@HiltAndroidApp
class Bron24Application : Application() {

    @Inject
    lateinit var localeManager: LocaleManager

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // LocaleManager will be applied in onCreate after injection
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("3c062e82-1411-437c-9157-f7e96556b098")
        localeManager.applySavedLocale(this)
    }
}
