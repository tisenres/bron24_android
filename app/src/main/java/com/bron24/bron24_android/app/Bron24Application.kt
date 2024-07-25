package com.bron24.bron24_android.app

import android.app.Application
import android.content.Context
import com.bron24.bron24_android.helper.util.LocaleManager
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Bron24Application : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("3c062e82-1411-437c-9157-f7e96556b098")
        MapKitFactory.initialize(this)
    }
}