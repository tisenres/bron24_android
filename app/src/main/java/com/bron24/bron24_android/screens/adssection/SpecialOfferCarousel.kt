package com.bron24.bron24_android.screens.adssection

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AdsSection(
    imageUrls: List<String>,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(pageCount = { imageUrls.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (imageUrls.isNotEmpty()) {
            delay(10000)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % imageUrls.size
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 1000)
                )
            }
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            AdsImageSection(
                images = imageUrls,
                pagerState = pagerState,
            )
        }
    }
}

@Composable
fun SpecialOfferCarousel(
    uiState: State<HomePageContract.UIState>,
    autoScrollDuration: Long = 5000L
) {

    val pageCount = uiState.value.specialOffers.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not() && pageCount > 0) {
        with(pagerState) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = autoScrollDuration)
                    val nextPage = (currentPage + 1).mod(pageCount)
                    animateScrollToPage(page = nextPage)
                    currentPageKey = nextPage
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
        pageSpacing = 20.dp
    ) {
        OfferImage(uiState.value.specialOffers[it].imageUrl)
    }
}


@Composable
fun AdsImageSection(images: List<String>, pagerState: PagerState) {
    Column(
        modifier = Modifier
            .height(153.dp)
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
fun OfferImage(imageRes: String) {
//    AsyncImage(
//        model = imageRes,
//        contentDescription = "offer_image",
//        modifier = Modifier
//            .fillMaxSize()
//            .clip(RoundedCornerShape(10.dp))
//            .clickable {
//                Log.d("OfferImage", "Image clicked")
//            },
//        contentScale = androidx.compose.ui.layout.ContentScale.Crop
//    )

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageRes)
            .placeholder(R.drawable.placeholder)
            .scale(Scale.FILL)
            .crossfade(true)
            .build(),
        contentDescription = "Offer Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
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

