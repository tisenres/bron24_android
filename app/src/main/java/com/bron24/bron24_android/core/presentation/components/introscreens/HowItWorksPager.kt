package com.bron24.bron24_android.core.presentation.components.introscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bron24.bron24_android.core.domain.model.Screen

@Composable
fun HowItWorksPager(navController: NavHostController) {
    val pagerState = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier.weight(1f),
            state = rememberLazyListState()
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0..1) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .background(if (pagerState.value == i) Color.Gray else Color.LightGray, shape = CircleShape)
                )
            }
        }
    }
}