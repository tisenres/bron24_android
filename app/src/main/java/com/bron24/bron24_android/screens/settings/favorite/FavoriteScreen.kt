package com.bron24.bron24_android.screens.settings.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.VenueItem
import com.bron24.bron24_android.screens.main.theme.White
import org.orbitmvi.orbit.compose.collectAsState

class FavoriteScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: FavoriteScreenContract.ViewModel = getViewModel<FavoriteScreenVM>()
        val state = viewModel.collectAsState()
        FavoriteScreenContent(state, viewModel::onDispatcher)
    }
}

@Composable
private fun FavoriteScreenContent(
    state: State<FavoriteScreenContract.UiState>,
    intent: (FavoriteScreenContract.Intent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomAppBar(title = "Favorites", startIcons = {
            Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "")
        }, listenerBack = {

        })
        LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
            items(10) {

            }
        }
    }
}