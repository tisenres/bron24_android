package com.bron24.bron24_android.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bron24.bron24_android.screens.main.Screen
import com.bron24.bron24_android.screens.searchfilter.SearchView
import com.bron24.bron24_android.screens.venuelisting.VenueListingView
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun HomePage(navController: NavController) {
    val listState = rememberLazyListState()

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
                viewModel = hiltViewModel(),
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
            )

            VenueListingView(
                navController = navController,
                listState = listState,
            )
        }
    }
}