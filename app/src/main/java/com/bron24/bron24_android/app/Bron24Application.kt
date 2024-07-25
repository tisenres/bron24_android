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
        // Set the Yandex MapKit API key
        MapKitFactory.setApiKey("d3dbe533-78a4-4794-b09d-0c4e636ea0fa")
    }
}