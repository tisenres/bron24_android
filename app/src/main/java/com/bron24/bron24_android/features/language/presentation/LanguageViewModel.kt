package com.bron24.bron24_android.features.language.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.core.util.LocaleManager
import com.bron24.bron24_android.features.language.domain.model.Language
import com.bron24.bron24_android.features.language.domain.usecases.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.features.language.domain.usecases.UpdateSelectedLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val updateSelectedLanguageUseCase: UpdateSelectedLanguageUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _selectedLanguage = MutableStateFlow<Language?>(null)
    val selectedLanguage: StateFlow<Language?> = _selectedLanguage

    init {
        viewModelScope.launch {
            _selectedLanguage.value = getAvailableLanguagesUseCase().firstOrNull()
        }
    }

    fun selectLanguage(language: Language) {
        _selectedLanguage.value = language
        LocaleManager.setLocale(context, language.code)
    }

    fun confirmLanguageSelection() {
        viewModelScope.launch {
            _selectedLanguage.value?.let { language ->
                updateSelectedLanguageUseCase(language)
                LocaleManager.setLocale(context, language.code)
            }
        }
    }
}
