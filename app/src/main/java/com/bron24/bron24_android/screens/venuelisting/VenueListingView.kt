package com.bron24.bron24_android.screens.venuelisting

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.AppButton
import com.bron24.bron24_android.components.items.VenueItem
import com.bron24.bron24_android.components.items.VenueLoadingPlaceholder
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.screens.adssection.SpecialOfferCarousel
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.runBlocking

//var filterCallback: (FilterOptions) -> Unit = {}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueListingView(
    state: State<HomePageContract.UIState>,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    refreshVenue: () -> Unit,
    clickSort: (String) -> Unit,
    listenerItem: (Venue) -> Unit,
) {
    var sortExpanded by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullToRefreshState()

//    filterCallback = {filterOptions->
//        Log.d("SSS", "VenueListingView: openCalll")
//        viewModel.updateFilterOptions(filterOptions = filterOptions)
//    }
    PullToRefreshBox(
        state = pullRefreshState,
        onRefresh = {
            refreshVenue.invoke()
        },
        isRefreshing = state.value.refresh,
        modifier = modifier,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullRefreshState,
                isRefreshing = state.value.refresh,
                color = MaterialTheme.colorScheme.tertiary,
                containerColor = Color.White,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        if (state.value.itemData.isEmpty() && !state.value.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.not_found),
                    contentDescription = "icon",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Not found!",
                    fontSize = 16.sp,
                    fontFamily = gilroyFontFamily,
                    color = GrayLight,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 20.dp)
                )

                AppButton(text = "Restart", modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(0.6f)) {
                    refreshVenue.invoke()
                }
            }
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item(key = "ads") {
                    if (state.value.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                    } else if (state.value.specialOffers.isNotEmpty()) {
                        SpecialOfferCarousel(uiState = state)
                    }
//                AdsSection(imageUrls = state.value.offers.map { it.image })
                }
                item(key = "title") {
                    if (state.value.isLoading) {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(0.5f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmer()
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 16.dp, top = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.recommendations),
                                style = TextStyle(
                                    fontFamily = interFontFamily,
                                    fontWeight = FontWeight(600),
                                    fontSize = 22.sp,
                                    lineHeight = 24.sp,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            )
//                    SortRow(
//                        onSortClick = { sortExpanded = true }
//                      )
                        }
                    }
                }
                if (state.value.isLoading) {
                    items(5) {
                        VenueLoadingPlaceholder()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    items(state.value.itemData) { venue ->
                        VenueItem(venue = venue, onFavoritesClick = listenerItem)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    runBlocking {  }
                }
            }
        }
    }
}

//    if (sortExpanded) {
//        AlertDialog(
//            onDismissRequest = { sortExpanded = false },
//            title = { Text("Sort by") },
//            text = {
//                Column {
//                    SortOption.entries.forEach { option ->
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    clickSort.invoke(option.name)
//                                    sortExpanded = false
//                                }
//                                .padding(vertical = 8.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            RadioButton(
//                                selected = option.name==state.value.selectedSort,
//                                onClick = null
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text(
//                                text = when (option) {
//                                    SortOption.MIN_TO_MAX -> "Price: Low to High"
//                                    SortOption.MAX_TO_MIN -> "Price: High to Low"
//                                    SortOption.CLOSEST -> "Closest"
//                                },
//                            )
//                        }
//                    }
//                }
//            },
//            confirmButton = {
//                TextButton(onClick = { sortExpanded = false }) {
//                    Text("Close")
//                }
//            }
//        )
//    }
//}

//    @Composable
//    fun SortRow(
//        onSortClick: () -> Unit
//    ) {
//        val interactionSource = remember { MutableInteractionSource() }
//
//        Row(
//            modifier = Modifier
//                .selectable(
//                    selected = true,
//                    onClick = onSortClick,
//                    interactionSource = interactionSource,
//                    indication = ripple()
//                ),
//            horizontalArrangement = Arrangement.spacedBy(3.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_sort_24),
//                contentDescription = "Sort",
//                tint = Color(0xFF32B768),
//                modifier = Modifier.size(22.dp)
//            )
//            Text(
//                text = stringResource(id = R.string.sort_by),
//                style = TextStyle(
//                    fontFamily = interFontFamily,
//                    fontWeight = FontWeight(600),
//                    fontSize = 16.sp,
//                    lineHeight = 22.sp,
//                    color = Color(0xFF32B768) // Accent color for the text
//                ),
//                modifier = Modifier.align(Alignment.CenterVertically),
//            )
//        }
//    }