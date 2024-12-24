package com.bron24.bron24_android.screens.settings.language

import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.screens.settings.language.LanguageContract.Intent.MoveBack
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LanguageVM @Inject constructor(private val direction: LanguageContract.Direction) : ViewModel(), LanguageContract.ViewModel {
    override fun onDispatchers(intent: LanguageContract.Intent) = intent {
        when (intent) {
            MoveBack -> {
                direction.moveBack()
            }
        }
    }

    override val container = container<LanguageContract.UIState, LanguageContract.SideEffect>(LanguageContract.UIState())
}