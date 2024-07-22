package com.bron24.bron24_android.features.language.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.features.language.domain.entities.Language
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

    private val _availableLanguages = MutableStateFlow<List<Language>>(emptyList())
    val availableLanguages: StateFlow<List<Language>> = _availableLanguages

    init {
        viewModelScope.launch {
            _availableLanguages.value = getAvailableLanguagesUseCase()
        }
    }

    fun selectLanguage(language: Language) {
        _selectedLanguage.value = language
    }

    fun confirmLanguageSelection() {
        viewModelScope.launch {
            _selectedLanguage.value?.let { language ->
                updateSelectedLanguageUseCase(language)
//                LocaleManager.updateLocale(context, language.code)
            }
        }
    }
}
