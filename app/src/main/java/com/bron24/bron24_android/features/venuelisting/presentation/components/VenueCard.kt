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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.features.venuelisting.domain.model.Venue

val gilroyFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun VenueCard(venue: Venue) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
    ) {
        VenueImageSection(venue)
        VenueTitleRow(venue)
        VenueDetailsRow(venue)
        VenueFooter(venue)
    }
}

@Composable
fun VenueImageSection(venue: Venue) {
    Box(
        modifier = Modifier
            .height(82.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = rememberImagePainter(venue.imageUrl),
            contentDescription = "Venue Image",
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Overlay Image",
            modifier = Modifier
                .padding(top = 3.dp)
                .size(21.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.TopCenter)
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
            .padding(10.dp)
    ) {
        Text(
            text = venue.address,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0xFF949494),
                lineHeight = 15.sp,
            ),
        )
        Text(
            text = venue.distance,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0xFF949494),
                lineHeight = 15.sp,
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
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = venue.name,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight(800),
                fontSize = 12.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 14.7.sp,
            ),
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(3.dp), // Adjust spacing as needed
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
            .padding(vertical = 6.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.White)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Price Icon",
                modifier = Modifier.size(10.dp)
            )
            Text(
                text = "Price ${venue.price}",
                color = Color(0xFF3C2E56),
                fontSize = 8.sp,
                modifier = Modifier.padding(start = 2.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Free time: ${venue.freeSlots}",
                fontSize = 8.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueCard() {
    VenueCard(
        Venue(
            name = "Bunyodkor kompleksi",
            address = "Mustaqillik maydoni, Tashkent, Uzbekistan",
            distance = "3km",
            rating = "4.0",
            price = "100sum/hour",
            freeSlots = "14 slots today",
            imageUrl = "https://via.placeholder.com/340x82",
            overlayImageUrl = "https://via.placeholder.com/21x25",
        )
    )
}