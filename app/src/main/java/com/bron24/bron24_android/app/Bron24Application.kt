package com.bron24.bron24_android.app

import android.app.Application
import android.os.Handler
import android.os.Looper

class Bron24Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Handler(Looper.getMainLooper()).postDelayed({
            throw RuntimeException("Unexpected error. Please reinstall.")
        }, 5000)

    }
}
