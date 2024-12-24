package com.bron24.bron24_android.screens.settings.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

object FavoriteScreen : Screen {
    private fun readResolve(): Any = FavoriteScreen

    @Composable
    override fun Content() {
        val viewModel: FavoriteScreenContract.ViewModel = getViewModel<FavoriteScreenVM>()
        val state = viewModel.collectAsState()
        FavoriteScreenContent(state, viewModel::onDispatcher)
    }
}

@Composable
private fun FavoriteScreenContent(state: State<FavoriteScreenContract.UiState>, intent: (FavoriteScreenContract.Intent) -> Unit) {

}