package com.bron24.bron24_android.screens.howitworks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HowItWorksPager(
    onNavigateToAuthScreens: () -> Unit
) {
    val pagerState = rememberPagerState (pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (page) {
                    0 -> HowItWorksScreen1()
                    1 -> HowItWorksScreen2(onFinishClick = {
                        onNavigateToAuthScreens()
                    })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                onNavigateToAuthScreens()
            }) {
                Text(
                    text = stringResource(id = R.string.skip),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 17.15.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.78).sp
                    )
                )
            }

            PagerIndicator(pagerState = pagerState)

            TextButton(onClick = {
                if (pagerState.currentPage < 1) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    onNavigateToAuthScreens()
                }
            }) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF32B768),
                        lineHeight = 17.15.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.78).sp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(2) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color(0xFF32B768) else Color(0xFFD9D9D9)
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(10.dp)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}