package com.bron24.bron24_android.app

import android.app.Application
import android.content.Context
import com.bron24.bron24_android.BuildConfig
import com.bron24.bron24_android.helper.util.LocaleManager
import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptions
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.layoutinspector.PlutoLayoutInspectorPlugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.logger.PlutoTimberTree
import com.pluto.plugins.network.PlutoNetworkPlugin
import com.pluto.plugins.preferences.PlutoSharePreferencesPlugin
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.yandex.mapkit.MapKitFactory
import timber.log.Timber

@HiltAndroidApp
class Bron24Application : Application() {

//    @Inject
//    lateinit var localeManager: LocaleManager

//    override fun attachBaseContext(base: Context) {
//        val languageCode = "ru" // You can dynamically set this value based on your app's logic
//        super.attachBaseContext(LocaleManager.updateLocale(base, languageCode))
//    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("905faf4b-e40f-4fc3-b1e5-a1043a3ab4ae")
        MapKitFactory.initialize(this)
    }

    private fun installTimber() {
//        Timber.plant(Timber.DebugTree())
        Timber.plant(PlutoTimberTree())
    }
    private var listener:(()->Unit)?=null
    fun logOut(block:()->Unit){
        listener = block
    }

    private fun installPluto() {
        Pluto.Installer(this)
            .addPlugin(PlutoNetworkPlugin())
            .addPlugin(PlutoExceptionsPlugin())
            .addPlugin(PlutoLoggerPlugin())
            .addPlugin(PlutoSharePreferencesPlugin())
            .addPlugin(PlutoLayoutInspectorPlugin())
            .install()

        PlutoExceptions.setExceptionHandler { thread, throwable ->
            Timber.tag("exception_demo").w(
                throwable,
                "uncaught exception handled on thread: %s", thread.name,
            )
        }
        PlutoExceptions.setANRHandler { thread, exception ->
            Timber.tag("anr_demo").w(exception, "potential ANR detected on thread: %s", thread.name)
        }
//        PlutoExceptions.mainThreadResponseThreshold = 10_000

//        Pluto.open(PlutoNetworkPlugin.ID)
//        Pluto.open(PlutoExceptionsPlugin.ID)
//        Pluto.open(PlutoLoggerPlugin.ID)
//        Pluto.open(PlutoSharePreferencesPlugin.ID)
//        Pluto.open(PlutoLayoutInspectorPlugin.ID)
    }
}
