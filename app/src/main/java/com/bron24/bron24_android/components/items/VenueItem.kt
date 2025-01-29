package com.bron24.bron24_android.components.items

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.screens.main.theme.FavoriteItemBackGround
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.google.android.play.integrity.internal.c
import com.valentinilk.shimmer.shimmer

@Composable
fun VenueItem(
    venue: Venue,
    listener: (Venue) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = FavoriteItemBackGround)
            .clickable {
                listener.invoke(venue)
            }
    ) {
        VenueImageSection(venue)
        VenueTitleRow(venue)
        VenueDetailsRow(venue)
        VenueFooter(venue)
    }
}

@Composable
fun VenueLoadingPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .shimmer()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        )
    }
}


@Composable
fun VenueImageSection(venue: Venue) {
    var showLoading by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .height(162.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        if (showLoading) {
            InitPhoto()
        }
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberAsyncImagePainter(
                model = venue.previewImage,
                onLoading = {
                    showLoading = true
                },
                onSuccess = {
                    showLoading = false
                },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = venue.venueName,
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun VenueDetailsRow(venue: Venue) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = venue.address.addressName,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF949494),
                lineHeight = 21.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        if(venue.distance.toInt()!=0){
            Text(
                text = String.format("%.1f", venue.distance) + " " + stringResource(id = R.string.km),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF949494),
                    lineHeight = 21.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

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
            text = venue.venueName,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight(800),
                fontSize = 16.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 19.6.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier.padding(start = 10.dp) // Adding padding to ensure spacing between title and rating
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Rating Image",
                modifier = Modifier
                    .width(14.dp)
                    .height(14.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = venue.rate.toString(),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 14.4.sp,
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
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
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = venue.pricePerHour + " " + stringResource(id = R.string.som_per_hour),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 14.sp,
                        color = Color(0xFF3C2E56),
                        lineHeight = 16.sp,
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.free_time) + ":",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 14.sp,
                        color = Color(0xFF3C2E56),
                        lineHeight = 16.sp,
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Text(
                    text = venue.slots.toString() + " " + stringResource(id = R.string.hours),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight(800),
                        fontSize = 14.sp,
                        color = Color(0xFF26A045),
                        lineHeight = 16.sp,
                    ),
                    modifier = Modifier.align(Alignment.Bottom),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}