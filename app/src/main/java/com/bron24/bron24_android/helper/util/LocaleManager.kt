package com.bron24.bron24_android.helper.util

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleManager @Inject constructor(
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase
) {

    fun setLocale(context: Context, languageCode: String) {
        Log.d("LocaleManager", "Setting locale to: $languageCode")
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        Log.d("LocaleManager", "Locale set to: ${Locale.getDefault().language}")
    }

    fun applySavedLocale(context: Context) {
        val selectedLanguageCode = getSelectedLanguageUseCase.execute().languageCode
        Log.d("SELECTED_LANG", selectedLanguageCode)
        setLocale(context, selectedLanguageCode)
    }
}