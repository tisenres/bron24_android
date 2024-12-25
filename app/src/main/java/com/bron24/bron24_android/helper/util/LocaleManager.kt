package com.bron24.bron24_android.helper.util

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleManager @Inject constructor(
  @ApplicationContext val context: Context, private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase
) {
  fun changeLanguage(languageCode: String) {
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

  fun getLanguageCode(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      context.getSystemService(LocaleManager::class.java).applicationLocales[0]?.toLanguageTag()?.split("-")?.first()
        ?: "en"
    } else {
      //version < 13
      AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()?.split("-")?.first() ?: "en"
    }
  }

  fun applySavedLocale() {
    val languageCode = getSelectedLanguageUseCase.invoke().languageCode
    changeLanguage(languageCode)
  }
}