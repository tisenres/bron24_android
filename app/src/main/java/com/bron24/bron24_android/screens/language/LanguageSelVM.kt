package com.bron24.bron24_android.screens.language

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import com.bron24.bron24_android.helper.util.LocaleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    override fun onDispatchers(intent: LanguageSelContract.Intent): Job = intent {
        when (intent) {
            is LanguageSelContract.Intent.SelectedLanguage -> {
//                reduce { state.copy(selectedLanguage = intent.language) }
                setUserLanguageUseCase.invoke(intent.language).onEach {
                    it.onSuccess {
                        reduce { state.copy(selectedLanguage = intent.language) }
                    }
                }.launchIn(viewModelScope)
            }
            LanguageSelContract.Intent.ClickMoveTo->{
                localStorage.openLanguageSel = true
                direction.moveToNext()
            }
        }
    }

    override fun initData(): Job = intent {
        getAvailableLanguagesUseCase.invoke().onEach {
            it.onSuccess {
                reduce { state.copy(languages = it) }
                Log.d("AAA", "initData: $it")
                val selectedLanguage = getSelectedLanguageUseCase.invoke()
                reduce { state.copy(selectedLanguage) }
            }.onFailure {
                Log.d("AAA", "initData: ${it.message}")
            }
        }.launchIn(viewModelScope)
    }

    override val container =
        container<LanguageSelContract.UIState, LanguageSelContract.SideEffect>(
            LanguageSelContract.UIState()
        )
}
