package com.bron24.bron24_android.screens.language

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val model: LanguageModel
) : ViewModel() {

    private val _selectedLanguage = MutableStateFlow(Language("uz", "O`zbek"))
    val selectedLanguage: StateFlow<Language> = _selectedLanguage

    private val _availableLanguages = MutableStateFlow<List<Language>>(emptyList())
    val availableLanguages: StateFlow<List<Language>> = _availableLanguages

    init {
        viewModelScope.launch {
            _availableLanguages.value = model.getAvailableLanguagesUseCase()
        }
    }

    fun selectLanguage(context: Context, language: Language) {
        if (_selectedLanguage.value != language) {
            _selectedLanguage.value = language
            model.selectLanguage(context, language)
        }
    }

    fun confirmLanguageSelection(context: Context) {
        viewModelScope.launch {
            model.updateSelectedLanguageUseCase(context, _selectedLanguage.value)
        }
    }
}