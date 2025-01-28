package com.bron24.bron24_android.screens.orderdetails.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.InitPhoto

@Composable
fun OrderDetailsImagePager(imageUrls:List<String>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })
    Box(
        modifier = modifier
            .height(180.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Gray)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(imageUrl = imageUrls[page], page = page)
        }
        ImageOverlay(
            currentPage = pagerState.currentPage,
            totalPages = imageUrls.size,
        )
    }
}

@Composable
fun Image(imageUrl: String, page: Int) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    if(isLoading){
        InitPhoto()
    }
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl, onLoading = {isLoading = true}, onSuccess = {isLoading = false}),
        contentDescription = "Venue Image $page",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ImageOverlay(
    currentPage: Int,
    totalPages: Int
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BottomIndicators(
            currentPage = currentPage,
            totalPages = totalPages,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 11.5.dp)
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
                    .height(1.5.dp)
                    .padding(end = 3.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .width(28.dp)
                    .background(if (index == currentPage) Color.White else Color(0xFFB7B3B3))
            )
        }
    }
}