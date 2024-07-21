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
import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue
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
            text = venue.address.toString(),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF949494),
                lineHeight = 21.sp,
            ),
        )
        Text(
            text = venue.contact2,
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
            text = venue.venueName,
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
                text = venue.sportType,
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
                    text = "Price: ${venue.pricePerHour}",
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
                    text = venue.description,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 12.sp,
                        color = Color(0xFF26A045),
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
//    VenueCard(
//        venue = Venue(
//            venueId = 1,
//            name = "Sample Venue",
//            type = "Indoor",
//            surface = "Grass",
//            capacity = 100,
//            sportType = "Football",
//            price = "100",
//            description = "Sample description",
//            workingHoursFrom = "09:00",
//            workingHoursTill = "21:00",
//            contact1 = "1234567890",
//            contact2 = null,
//            city = 1,
//            infrastructure = 1,
//            address = 3,
//            owner = 1,
//            distance = "5 km",
//            rating = "4.5",
//            freeSlots = "10",
//            imageUrl = "",
//            overlayImageUrl = ""
//        ),
//        isLoading = false
//    )
}