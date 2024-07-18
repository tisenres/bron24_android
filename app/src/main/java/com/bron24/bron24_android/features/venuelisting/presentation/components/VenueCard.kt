package com.bron24.bron24_android.features.venuelisting.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.features.venuelisting.domain.model.Venue
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter

@Composable
fun VenueCard(venue: Venue) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .size(340.dp, 171.dp)
    ) {
        Box(
            modifier = Modifier
                .size(340.dp, 82.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberImagePainter(venue.imageUrl),
                contentDescription = "Venue Image",
                modifier = Modifier
                    .size(340.dp, 82.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Image(
                painter = rememberImagePainter(venue.overlayImageUrl),
                contentDescription = "Overlay Image",
                modifier = Modifier
                    .size(21.25.dp, 25.23.dp)
                    .offset(x = 151.94.dp, y = (-24.60).dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(320.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = venue.name,
                    fontSize = 12.sp,
                    color = Color(0xFF3C2D56)
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Text(
                        text = venue.rating,
                        fontSize = 12.sp,
                        color = Color(0xFF3C2D56)
                    )
                }
            }
            Text(
                text = "${venue.address}                                            ${venue.distance}",
                fontSize = 10.sp,
                lineHeight = 15.sp,
                color = Color(0xFF949494)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier.fillMaxWidth().height(17.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
                    .background(Color.Transparent)
                    .border(0.5.dp, Color.White)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
            ) {
                Text(
                    text = "Price ${venue.price}",
                    fontSize = 8.sp,
                    color = Color(0xFF3C2D56)
                )
                Text(
                    text = "Free time: ${venue.freeSlots}",
                    fontSize = 8.sp,
                    color = Color(0xFF3C2D56)
                )
            }
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
            overlayImageUrl = "https://via.placeholder.com/21x25"
        )
    )
}
