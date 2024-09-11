package com.bron24.bron24_android.screens.adssection

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bron24.bron24_android.R

@Composable
fun AdsSection(modifier: Modifier = Modifier) {
    val images = remember {
        listOf(
            R.drawable.offer_image_1,
            R.drawable.view_soccer_ball,
            R.drawable.offer_image_1,
            R.drawable.view_soccer_ball
        )
    }
    val pagerState = rememberPagerState(pageCount = { images.size })

    // Ensure that confetti state is handled properly

    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            AdsImageSection(
                images = images,
                pagerState = pagerState,
            )
        }

    }
}

@Composable
fun AdsImageSection(images: List<Int>, pagerState: PagerState) {
    Column(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                OfferImage(imageRes = images[page])
            }
        }

//        Spacer(modifier = Modifier.height(16.dp))
//
//        BottomIndicators(
//            currentPage = pagerState.currentPage,
//            totalPages = images.size,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
    }
}

@Composable
fun OfferImage(imageRes: Int) {
    AsyncImage(
        model = imageRes,
        contentDescription = "offer_image",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                Log.d("OfferImage", "Image clicked")
            },
        contentScale = androidx.compose.ui.layout.ContentScale.Crop
    )
}

@Composable
fun BottomIndicators(currentPage: Int, totalPages: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .clip(if (currentPage == index) RoundedCornerShape(10.dp) else CircleShape)
                    .background(
                        color = if (currentPage == index) Color(0xFF32B768) else Color(0xFFD9D9D9)
                    )
                    .size(
                        width = if (currentPage == index) 20.dp else 10.dp,
                        height = 10.dp
                    )
            )
        }
    }
}

