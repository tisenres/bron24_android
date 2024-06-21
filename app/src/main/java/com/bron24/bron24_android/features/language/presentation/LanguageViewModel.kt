package com.bron24.bron24_android.features.language.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.features.language.domain.model.Language
import com.bron24.bron24_android.features.language.domain.util.LocaleManager
import com.bron24.bron24_android.features.language.domain.usecases.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.features.language.domain.usecases.UpdateSelectedLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    application: Application,
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val updateSelectedLanguageUseCase: UpdateSelectedLanguageUseCase
) : AndroidViewModel(application) {

    private val _selectedLanguage = MutableStateFlow<Language?>(null)
    val selectedLanguage: StateFlow<Language?> = _selectedLanguage

    init {
        viewModelScope.launch {
            _selectedLanguage.value = getAvailableLanguagesUseCase.execute().firstOrNull()
        }
    }

    fun selectLanguage(language: Language) {
        _selectedLanguage.value = language
    }

    fun confirmLanguageSelection() {
        viewModelScope.launch {
            _selectedLanguage.value?.let {
                updateSelectedLanguageUseCase.execute(it)
                LocaleManager.setLocale(getApplication(), it.code)
            }
        }
    }
}