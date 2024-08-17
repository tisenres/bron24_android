package com.bron24.bron24_android.screens.adssection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.interFontFamily

@OptIn(ExperimentalFoundationApi::class)
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

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.special_offers),
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = androidx.compose.ui.text.font.FontWeight(600),
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color.Black
                )
            )
            Text(
                text = stringResource(id = R.string.see_all),
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = androidx.compose.ui.text.font.FontWeight(600),
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    color = Color(0xFF32B768)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AdsImageSection(images, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
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

        Spacer(modifier = Modifier.height(16.dp))

        BottomIndicators(
            currentPage = pagerState.currentPage,
            totalPages = images.size,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
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

@Composable
fun OfferImage(imageRes: Int) {
    AsyncImage(
        model = imageRes,
        contentDescription = "offer_image",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp)),
        contentScale = androidx.compose.ui.layout.ContentScale.Crop
    )
}