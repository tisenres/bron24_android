package com.bron24.bron24_android.screens.adssection

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import kotlinx.coroutines.delay

@Composable
fun AdsSection(modifier: Modifier = Modifier) {
    var currentPage by remember { mutableIntStateOf(0) }
    val imagesCount = 4
    val autoScrollInterval = 10000L

    LaunchedEffect(Unit) {
        while (true) {
            delay(autoScrollInterval)
            currentPage = (currentPage + 1) % imagesCount
        }
    }

    Column(modifier = modifier) {
        HeaderSection()
        ImageCarousel(currentPage, imagesCount) { dragAmount ->
            currentPage = if (dragAmount > 0) {
                (currentPage - 1).coerceAtLeast(0)
            } else {
                (currentPage + 1) % imagesCount
            }
        }
        Indicators(currentPage, imagesCount)
    }
}

@Composable
fun HeaderSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.special_offers),
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black
            )
        )
        Text(
            text = stringResource(id = R.string.see_all),
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF32B768)
            )
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ImageCarousel(currentPage: Int, imagesCount: Int, onSwipe: (Float) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
            .clip(RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    onSwipe(dragAmount)
                }
            }
    ) {
        Crossfade(targetState = currentPage) { page ->
            OfferImage(page)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Indicators(currentPage: Int, imagesCount: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(imagesCount) { index ->
            val indicatorWidth by animateDpAsState(
                targetValue = if (currentPage == index) 20.dp else 10.dp
            )
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(indicatorWidth)
                    .clip(CircleShape)
                    .background(
                        color = if (currentPage == index) Color(0xFF32B768) else Color(0xFFD9D9D9)
                    )
            )
        }
    }
}

@Composable
fun OfferImage(page: Int) {
    val imageRes = when (page) {
        0 -> R.drawable.ic_metro
        1 -> R.drawable.ic_metro
        2 -> R.drawable.ic_metro
        else -> R.drawable.ic_metro
    }
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Offer Image",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}