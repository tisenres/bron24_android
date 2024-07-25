package com.bron24.bron24_android.features.adssection.presentation

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.core.presentation.theme.interFontFamily

@Composable
fun AdsSection(modifier: Modifier = Modifier) {
    var currentPage by remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Special Offers",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    color = Color.Black
                )
            )
            Text(
                text = "See all",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF32B768)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 0) {
                            currentPage = (currentPage - 1).coerceAtLeast(0)
                        } else {
                            currentPage = (currentPage + 1).coerceAtMost(2)
                        }
                    }
                }
        ) {
            OfferImage(currentPage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = if (currentPage == index) Color(0xFF32B768) else Color(0xFFD9D9D9),
                            shape = CircleShape
                        )
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
fun OfferImage(page: Int) {
    val imageRes = when (page) {
        0 -> R.drawable.offer_image_1
        1 -> R.drawable.offer_image_1
        2 -> R.drawable.offer_image_1
        else -> R.drawable.offer_image_1
    }
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "offer_image",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewAdsSection() {
    AdsSection()
}