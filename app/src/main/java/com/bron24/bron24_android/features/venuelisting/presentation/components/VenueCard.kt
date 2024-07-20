package com.bron24.bron24_android.features.venuelisting.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.features.venuelisting.domain.model.Venue
import com.valentinilk.shimmer.shimmer

val gilroyFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun VenueCard(venue: Venue, isLoading: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF4FEF4).copy(alpha = 0.47f), RoundedCornerShape(10.dp))
            .let {
                if (isLoading) {
                    it.shimmer()
                } else {
                    it
                }
            }
    ) {
        if (isLoading) {
            LoadingPlaceholder()
        } else {
            VenueImageSection(venue)
            VenueTitleRow(venue)
            VenueDetailsRow(venue)
            VenueFooter(venue)
        }
    }
}

@Composable
fun LoadingPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray.copy(alpha = 0.47f))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.47f))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.47f))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.47f))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.47f))
        )
    }
}

@Composable
fun VenueImageSection(venue: Venue) {
    Box(
        modifier = Modifier
            .height(162.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.venue_pic),
            contentDescription = "Venue Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_favorite_24),
            contentDescription = "Overlay Image",
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .size(21.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun VenueDetailsRow(venue: Venue) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
    ) {
        Text(
            text = venue.address,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF949494),
                lineHeight = 21.sp,
            ),
        )
        Text(
            text = venue.distance,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF949494),
                lineHeight = 21.sp,
            )
        )
    }
}

@Composable
fun VenueTitleRow(venue: Venue) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = venue.name,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight(800),
                fontSize = 16.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 19.6.sp,
            ),
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Rating Image",
                modifier = Modifier
                    .width(15.dp)
                    .height(16.dp)
            )
            Text(
                text = venue.rating,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 14.4.sp,
                ),
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
    }
}

@Composable
fun VenueFooter(venue: Venue) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color(0xFFEBECEE))
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 15.dp, start = 10.dp, end = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dollar),
                    contentDescription = "Price Icon",
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Price: ${venue.price}",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 12.sp,
                        color = Color(0xFF3C2E56),
                        lineHeight = 14.7.sp,
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = "Free time:",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 12.sp,
                        color = Color(0xFF3C2E56),
                        lineHeight = 14.7.sp,
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Text(
                    text = venue.freeSlots,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 12.sp,
                        color = Color(0xFF32B768),
                        lineHeight = 14.7.sp,
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueCard() {
    VenueCard(
        venue = Venue(
            name = "Bunyodkor kompleksi",
            address = "Mustaqillik maydoni, Tashkent, Uzbekistan",
            distance = "3km",
            rating = "4.0",
            price = "100 000 sum/hour",
            freeSlots = "14 slots today",
            imageUrl = "https://via.placeholder.com/340x160",
            overlayImageUrl = "https://via.placeholder.com/21x25",
        ),
        isLoading = false // Change to false to see the actual content
    )
}