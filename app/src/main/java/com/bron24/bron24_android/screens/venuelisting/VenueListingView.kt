package com.bron24.bron24_android.screens.venuelisting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.adssection.AdsSection

@Composable
fun VenueListingView(
    navController: NavController,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    viewModel: VenueListingViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 25.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AdsSection(modifier = Modifier)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.football_fields),
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (isLoading) {
            items(5) {
                VenueCard(isLoading = true, navController = navController)
            }
        } else {
            items(venues) { venue ->
                VenueCard(venue = venue, isLoading = false, navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewVenueListingView() {
    val navController = rememberNavController()
    VenueListingView(navController = navController)
}