package com.bron24.bron24_android.screens.settings.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenVM @Inject constructor() : ViewModel(), FavoriteScreenContract.ViewModel {
    override fun onDispatcher(intent: FavoriteScreenContract.Intent) = intent {
        when (intent) {

        }
    }

    override val container = container<FavoriteScreenContract.UiState, FavoriteScreenContract.SideEffect>(FavoriteScreenContract.UiState())
}