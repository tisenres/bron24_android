package com.bron24.bron24_android.screens.menu_pages.home_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.SearchView
import com.bron24.bron24_android.screens.main.theme.Success
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
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.baseline_home_24))
            return TabOptions(
                index = 1u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        val viewModel: HomePageContract.ViewModel = getViewModel<HomePageVM>()
        remember {
            viewModel.initData()
        }
        val state = viewModel.collectAsState()
        HomePageContent(state = state, intent = viewModel::onDispatchers)
    }
}

@Composable
fun HomePageContent(
    state: State<HomePageContract.UISate>,
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


