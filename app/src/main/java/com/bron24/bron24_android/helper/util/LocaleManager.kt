package com.bron24.bron24_android.helper.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleManager @Inject constructor(
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase
) {

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

//    fun updateLocale(context: Context, languageCode: String): Context {
//        val locale = Locale(languageCode)
//        Locale.setDefault(locale)
//
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
//        config.setLayoutDirection(locale)
//
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            context.createConfigurationContext(config)
//        } else {
//            context.resources.updateConfiguration(config, context.resources.displayMetrics)
//            context
//        }
//    }

    fun applySavedLocale(context: Context) {
        val languageCode = getSelectedLanguageUseCase.execute().languageCode
        setLocale(context, languageCode)
    }
}