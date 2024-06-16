package com.bron24.bron24_android.features.language.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.features.language.domain.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _selectedLanguage = MutableStateFlow(Language.UZBEK)
    val selectedLanguage: StateFlow<Language> = _selectedLanguage

    fun selectLanguage(language: Language) {
        _selectedLanguage.value = language
    }

    fun confirmLanguageSelection() {
        viewModelScope.launch {
            saveSelectedLanguage(_selectedLanguage.value)
            updateLocale(getApplication(), _selectedLanguage.value.code)
        }
    }

    private fun saveSelectedLanguage(language: Language) {
        val sharedPreferences =
            getApplication<Application>()
                .getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("selected_language", language.code).apply()
    }

    private fun updateLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}