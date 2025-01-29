package com.bron24.bron24_android.screens.settings.language

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import com.bron24.bron24_android.screens.settings.language.LanguageContract.Intent.ChangeLanguage
import com.bron24.bron24_android.screens.settings.language.LanguageContract.Intent.MoveBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LanguageVM @Inject constructor(
  private val direction: LanguageContract.Direction,
  private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
  private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase,
  private val setUserLanguageUseCase: SetUserLanguageUseCase
) : ViewModel(), LanguageContract.ViewModel {

  override fun onDispatchers(intent: LanguageContract.Intent) = intent {
    when (intent) {
      MoveBack -> {
        direction.moveBack()
      }

      is ChangeLanguage -> {
        setUserLanguageUseCase.invoke(intent.language).onStart {
          reduce { state.copy(isLoading = true) }
        }.onEach {
          reduce { state.copy(selectedLanguageIndex = intent.index, selectedLanguage = intent.language) }
        }.onCompletion { reduce { state.copy(isLoading = false) } }.launchIn(viewModelScope)
      }
    }
  }

  override fun initData() = intent {
    getAvailableLanguagesUseCase.invoke().onStart {
      reduce { state.copy(isLoading = true) }
    }.onEach {
      it.onSuccess {
        reduce { state.copy(languageList = it) }
        val selectedLanguage = getSelectedLanguageUseCase.invoke()
        reduce { state.copy(selectedLanguage = selectedLanguage) }
        var langIndex = 0;
        it.forEachIndexed { index, item ->
          if (item == selectedLanguage) langIndex = index
        }
        reduce { state.copy(selectedLanguageIndex = langIndex) }
      }.onFailure {
        reduce { state.copy(error = it.message.toString()) }
      }
    }.onCompletion { reduce { state.copy(isLoading = false) } }.launchIn(viewModelScope)
  }

  override val container = container<LanguageContract.UIState, LanguageContract.SideEffect>(LanguageContract.UIState())
}
