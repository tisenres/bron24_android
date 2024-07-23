package com.bron24.bron24_android.core.presentation.components.introscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bron24.bron24_android.core.domain.model.Screen
import kotlinx.coroutines.launch

@Composable
fun HowItWorksPager(navController: NavHostController) {
    val pagerState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyRow(
            modifier = Modifier.weight(1f),
            state = pagerState,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(listOf(0, 1)) { index, _ ->
                Box(modifier = Modifier.fillParentMaxSize()) {
                    when (index) {
                        0 -> HowItWorksScreen1()
                        1 -> HowItWorksScreen2(onFinishClick = {
                            navController.navigate(Screen.LocationPermission.route) {
                                popUpTo(Screen.HowItWorksPager.route) { inclusive = true }
                            }
                        })
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                navController.navigate(Screen.LocationPermission.route) {
                    popUpTo(Screen.HowItWorksPager.route) { inclusive = true }
                }
            }) {
                Text(text = "Skip",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 17.15.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.028).em
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0..1) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(2.dp)
                            .background(
                                if (pagerState.firstVisibleItemIndex == i) Color.Green else Color.LightGray,
                                shape = CircleShape
                            )
                    )
                }
            }

            TextButton(onClick = {
                if (pagerState.firstVisibleItemIndex == 0) {
                    coroutineScope.launch {
                        pagerState.animateScrollToItem(1)
                    }
                } else {
                    navController.navigate(Screen.LocationPermission.route) {
                        popUpTo(Screen.HowItWorksPager.route) { inclusive = true }
                    }
                }
            }) {
                Text(text = "Next",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF32B768),
                        lineHeight = 17.15.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.028).em
                    )
                )
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.isScrollInProgress }.collect { isScrolling ->
                if (!isScrolling) {
                    coroutineScope.launch {
                        pagerState.animateScrollToItem(pagerState.firstVisibleItemIndex)
                    }
                }
            }
        }
    }
}