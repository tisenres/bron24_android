package com.bron24.bron24_android.screens.settings.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.FavoriteItem
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
    Column {
        CustomAppBar(title = "Favorites", startIcons = {
            Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "")
        }, listener = {

        })
        LazyColumn {
            items(10) {
                FavoriteItem()
            }
        }
    }
}