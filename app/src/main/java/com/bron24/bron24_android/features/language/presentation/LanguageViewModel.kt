package com.bron24.bron24_android.features.language.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.features.language.domain.entities.Language
import com.bron24.bron24_android.features.language.domain.usecases.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.features.language.domain.usecases.UpdateSelectedLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val updateSelectedLanguageUseCase: UpdateSelectedLanguageUseCase
) : ViewModel() {

    private val _selectedLanguage = MutableStateFlow<Language>(Language.UZBEK())
    val selectedLanguage: StateFlow<Language> = _selectedLanguage

    private val _availableLanguages = MutableStateFlow<List<Language>>(emptyList())
    val availableLanguages: StateFlow<List<Language>> = _availableLanguages

    init {
        viewModelScope.launch {
            _availableLanguages.value = getAvailableLanguagesUseCase()
            _selectedLanguage.value = Language.UZBEK()
        }
    }

    fun selectLanguage(language: Language) {
        _selectedLanguage.value = language
    }

    fun confirmLanguageSelection() {
        viewModelScope.launch {
            updateSelectedLanguageUseCase(_selectedLanguage.value)
        }
    }
}