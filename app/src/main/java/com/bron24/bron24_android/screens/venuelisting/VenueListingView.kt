package com.bron24.bron24_android.screens.venuelisting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.adssection.AdsSection

import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.bron24.bron24_android.screens.main.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueListingView(
    navController: NavController,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    viewModel: VenueListingViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentSortOption by viewModel.sortOption.collectAsState()

    var sortExpanded by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullRefreshState,
        onRefresh = { viewModel.refreshVenues() },
        isRefreshing = isLoading,
        modifier = modifier,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullRefreshState,
                isRefreshing = isLoading,
                color = Color(0xFF32B768),
                containerColor = Color.White,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(key = "ads") {
                Spacer(modifier = Modifier.height(16.dp))
                AdsSection(modifier = Modifier)
            }
            item(key = "title") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.football_fields),
                        style = TextStyle(
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    )
//                    Box(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(8.dp)) // Rounded corners for the box
//                            .background(Color.Gray.copy(alpha = 0.1f)) // Transparent gray background
//                            .clickable {
//                                sortExpanded = true
//                            }
//                            .padding(horizontal = 10.dp, vertical = 6.dp) // Padding to give some space around the text
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.sort_by),
//                            style = TextStyle(
//                                fontFamily = interFontFamily,
//                                fontWeight = FontWeight(600),
//                                fontSize = 14.sp,
//                                lineHeight = 19.sp,
//                                color = Color(0xFF32B768) // Accent color for the text
//                            )
//                        )
//                    }

                    SortRow(onSortClick = { sortExpanded = true })
                }
            }

            items(venues, key = { it.venueId }) { venue ->
                VenueCard(venue = venue, isLoading = false, navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    if (sortExpanded) {
        AlertDialog(
            onDismissRequest = { sortExpanded = false },
            title = { Text("Sort by") },
            text = {
                Column {
                    SortOption.values().forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateSortOption(option)
                                    sortExpanded = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentSortOption == option,
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (option) {
                                    SortOption.MIN_TO_MAX -> "Price: Low to High"
                                    SortOption.MAX_TO_MIN -> "Price: High to Low"
                                    SortOption.CLOSEST -> "Closest"
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { sortExpanded = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun SortRow(onSortClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .selectable(
                selected = false,
                onClick = onSortClick,
                interactionSource = interactionSource,
                indication = ripple()
            )
            .padding(4.dp), // Add some padding inside the ripple area
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_sort_24),
            contentDescription = "Sort",
            tint = Color(0xFF32B768),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = stringResource(id = R.string.sort_by),
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight(600),
                fontSize = 14.sp,
                lineHeight = 19.sp,
                color = Color(0xFF32B768) // Accent color for the text
            ),
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

@Composable
fun PreviewVenueListingView() {
//    val navController = rememberNavController()
//    VenueListingView(navController = navController)
    Row(modifier = Modifier
        .padding(horizontal = 10.dp, vertical = 6.dp)
        .clickable {  },
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_sort_24),
            contentDescription = "Sort",
            tint = Color(0xFF32B768),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = stringResource(id = R.string.sort_by),
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight(600),
                fontSize = 14.sp,
                lineHeight = 19.sp,
                color = Color(0xFF32B768) // Accent color for the text
            ),
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}