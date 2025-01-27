package com.bron24.bron24_android.screens.menu_pages.home_page

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.SearchView
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.util.hiltScreenModel
import com.bron24.bron24_android.screens.venuelisting.VenueListingView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState

object HomePage : Tab {
    private fun readResolve(): Any = HomePage
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.home)
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_home))
            return TabOptions(
                index = 1u,
                title = title,
                icon = icon
            )
        }


    @Composable
    override fun Content() {
        val viewModel:HomePageContract.ViewModel = hiltScreenModel()
        val state = viewModel.collectAsState()
        Log.d("AAA", "Content: init")
        HomePageContent(state = state, intent = viewModel::onDispatchers)
    }
}

@Composable
fun HomePageContent(
    state: State<HomePageContract.UIState>,
    intent: (HomePageContract.Intent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchView(
                name = state.value.firstName,
                modifier = Modifier.fillMaxWidth(),
                clickSearch = {
                    intent.invoke(HomePageContract.Intent.ClickSearch)
                },
                clickFilter = {
                    intent.invoke(HomePageContract.Intent.ClickFilter)
                }
            )
            VenueListingView(
                state = state,
                refreshVenue = { intent.invoke(HomePageContract.Intent.Refresh) },
                clickSort = { intent.invoke(HomePageContract.Intent.SelectedSort(it)) })
            {
                intent.invoke(HomePageContract.Intent.ClickItem(it.venueId))
            }
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Success)
}


