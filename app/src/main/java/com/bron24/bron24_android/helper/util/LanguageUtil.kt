package com.bron24.bron24_android.helper.util

import android.content.Context
import android.util.Log
import com.bron24.bron24_android.domain.entity.user.Language

fun setLanguage(language: Language, context: Context) {
  val config = context.resources.configuration
  val locale = java.util.Locale(language.languageCode)
  java.util.Locale.setDefault(locale)
  config.setLocale(locale)
  context.createConfigurationContext(config)
  context.resources.updateConfiguration(config, context.resources.displayMetrics)
}