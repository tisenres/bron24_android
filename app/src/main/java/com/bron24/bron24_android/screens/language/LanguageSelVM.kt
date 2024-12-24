package com.bron24.bron24_android.screens.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import com.bron24.bron24_android.helper.util.LocaleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LanguageSelVM @Inject constructor(
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val setUserLanguageUseCase: SetUserLanguageUseCase,
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase,
    private val localeManager: LocaleManager,
    private val direction: LanguageSelContract.Direction,
    private val localStorage: LocalStorage
) : ViewModel(), LanguageSelContract.ViewModel {
    private var langCode = ""
    private val _selectedLanguage = MutableStateFlow(Language("uz", "O`zbek"))
    val selectedLanguage: StateFlow<Language> = _selectedLanguage

    private val _availableLanguages = MutableStateFlow<List<Language>>(emptyList())
    val availableLanguages: StateFlow<List<Language>> = _availableLanguages


    override fun initData(): Job = intent {
        setUserLanguageUseCase.invoke(language = Language("uz", "O`zbek"))
//        getSelectedLanguageUseCase.invoke().onEach {
//            it.onSuccess {
////                localeManager.applySavedLocale()
//            }
//        }.launchIn(viewModelScope)
        getAvailableLanguagesUseCase.invoke().onEach {
            it.onSuccess { list ->
                reduce { state.copy(availableLanguages = list) }
            }
        }.launchIn(viewModelScope)
    }

//    fun selectLanguage(language: Language) {
//        if (_selectedLanguage.value != language) {
//            _selectedLanguage.value = language
//            localeManager.setLocale(language.languageCode)
//        }
//    }

//    fun confirmLanguageSelection(context: Context) {
//        viewModelScope.launch {
//            setUserLanguageUseCase.execute(language = _selectedLanguage.value)
//
//        }
//    }
    init {
        viewModelScope.launch(Dispatchers.Main) {
            //applySavedLocale()
            localeManager.changeLanguage(localeManager.getLanguageCode())
        }
    }
//    fun applySavedLocale() {
//        getSelectedLanguageUseCase.invoke().onEach{
//            it.onSuccess {
//                localeManager.changeLanguage(it.languageCode)
//            }.onFailure {
//                localeManager.changeLanguage("uz")
//            }
//        }.launchIn(viewModelScope)
//    }


    override fun onDispatchers(intent: LanguageSelContract.Intent): Job = intent {
        when (intent) {
            is LanguageSelContract.Intent.SelectedLanguage -> {
                viewModelScope.launch(Dispatchers.Main) {
                    if(langCode!=intent.language.languageCode){
                        localeManager.changeLanguage(intent.language.languageCode)
                        reduce { state.copy(langCode = intent.language.languageCode) }
                    }
                }
            }
            LanguageSelContract.Intent.ClickMoveTo->{
                localStorage.openLanguageSel = true
                direction.moveToNext()
            }
        }
    }

    override val container =
        container<LanguageSelContract.UISate, LanguageSelContract.SideEffect>(
            LanguageSelContract.UISate(
                langCode = localeManager.getLanguageCode()
            )
        )
}


//class LanguageModel @Inject constructor(
//    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
//    private val setUserLanguageUseCase: SetUserLanguageUseCase,
//    private val localeManager: LocaleManager
//) {
////    fun getAvailableLanguagesUseCase(): List<Language> {
////        return getAvailableLanguagesUseCase.execute()
////    }
//
////    fun updateSelectedLanguageUseCase(context: Context, language: Language) {
////        setUserLanguageUseCase.invoke(language)
////        localeManager.setLocale(language.languageCode)
////    }
////
////    fun selectLanguage(language: Language) {
////        localeManager.setLocale(language.languageCode)
////    }
//}