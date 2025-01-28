package com.bron24.bron24_android.screens.adssection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.bron24.bron24_android.components.items.LoadingScreen
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp)),
        pageSpacing = 20.dp
    ) {
        OfferImage(uiState.value.specialOffers[it].imageUrl)
    }
}

@Composable
fun OfferImage(imageRes: String) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.2f))
        )
        var showLoading by remember {
            mutableStateOf(false)
        }
        if(showLoading){
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(
                    color = Color(0xFF32B768),
                    modifier = Modifier.align(Alignment.Center).size(32.dp)
                )
            }
        }
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberAsyncImagePainter(
                model = imageRes,
                onLoading = {
                    showLoading = true
                },
                onSuccess = {
                    showLoading = false
                },
            ),
            contentDescription = imageRes,
            contentScale = ContentScale.Crop
        )
    }
}