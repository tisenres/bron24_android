package com.bron24.bron24_android.app

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Bron24Application : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("905faf4b-e40f-4fc3-b1e5-a1043a3ab4ae")
        MapKitFactory.initialize(this)
    }
}
