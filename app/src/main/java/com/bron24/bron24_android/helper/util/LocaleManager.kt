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

    fun applySavedLocale(context: Context) {
        val languageCode = getSelectedLanguageUseCase.execute().languageCode
        setLocale(context, languageCode)
    }
}