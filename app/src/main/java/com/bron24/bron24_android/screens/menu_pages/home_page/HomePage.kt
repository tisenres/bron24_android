package com.bron24.bron24_android.screens.menu_pages.home_page

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
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
import com.bron24.bron24_android.screens.search.filter_screen.FilterScreenContent
import com.bron24.bron24_android.screens.util.hiltScreenModel
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContent
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContract
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreenContent
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
        val viewModel: HomePageContract.ViewModel = hiltScreenModel()
        val state = viewModel.collectAsState()
        HomePageContent(state = state, intent = viewModel::onDispatchers)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomePageContent(
    state: State<HomePageContract.UIState>,
    intent: (HomePageContract.Intent) -> Unit
) {
    val listState = rememberLazyListState()
    var openFilter by remember {
        mutableStateOf(false)
    }
    var openVenueDetails by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val activity  = context as Activity
    BackHandler {
        when{
            openFilter -> openFilter = false
            openVenueDetails -> openVenueDetails = false
            else -> activity.finish()
        }
    }
    if (openFilter) {
        FilterScreenContent(
            clickBack = {
                openFilter = !openFilter
            }
        ) {
            openFilter = !openFilter
        }
    } else if (openVenueDetails) {
        val states= mutableStateOf(VenueDetailsContract.UIState(state.value.isLoading, venue = state.value.venueDetails, imageUrls = state.value.imageUrls))
        VenueDetailsScreenContent(states, back =  {
            openVenueDetails = false
        }){
            openVenueDetails = false
            intent.invoke(HomePageContract.Intent.ClickOrder(it))
        }
    } else {
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
                        openFilter = true
                    }
                )
                VenueListingView(
                    state = state,
                    listState = listState,
                    refreshVenue = { intent.invoke(HomePageContract.Intent.Refresh) },
                    clickSort = { intent.invoke(HomePageContract.Intent.SelectedSort(it)) })
                {
                    intent.invoke(HomePageContract.Intent.ClickItem(it.venueId))
                    openVenueDetails = true
                }
            }
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Success)
}


