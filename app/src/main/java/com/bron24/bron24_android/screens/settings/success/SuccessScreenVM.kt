package com.bron24.bron24_android.screens.settings.success

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SuccessScreenVM @Inject constructor() : ViewModel(), SuccessScreenContract.ViewModel {
    override fun onDispatchers(intent: SuccessScreenContract.Intent) = intent {
        when (intent) {

        }
    }

    override val container = container<SuccessScreenContract.UiState, SuccessScreenContract.SideEffect>(SuccessScreenContract.UiState())
}