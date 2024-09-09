package com.bron24.bron24_android.screens.map

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import com.bron24.bron24_android.screens.venuedetails.AddressRow
import com.bron24.bron24_android.screens.venuedetails.AvailableSlots
import com.bron24.bron24_android.screens.venuedetails.DistanceRow
import com.bron24.bron24_android.screens.venuedetails.copyAddressToClipboard

@Composable
fun SmallVenueDetailsScreen(
    modifier: Modifier,
    venueDetails: VenueDetails?,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    val isLoading by remember { derivedStateOf { venueDetails == null } }

    if (isLoading) {
        LoadingScreen()
    } else {
        SmallDetailsContent(
            modifier = modifier,
            venueDetails = venueDetails,
            isFavorite = isFavorite,
            favoriteScale = scale,
            onFavoriteClick = { isFavorite = !isFavorite },
            onCopyAddressClick = { copyAddressToClipboard(context, venueDetails?.address?.addressName) },
            onClose = onClose
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircularProgressIndicator(
            color = Color(0xFF32B768),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SmallDetailsContent(
    modifier: Modifier,
    venueDetails: VenueDetails?,
    isFavorite: Boolean,
    favoriteScale: Float,
    onFavoriteClick: () -> Unit,
    onCopyAddressClick: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(RoundedCornerShape(10.dp))
    ) {
        SmallImageSection(venueDetails, isFavorite, favoriteScale, onFavoriteClick)
        Spacer(modifier = Modifier.height(12.dp))
        SmallHeaderSection(venueDetails, onCopyAddressClick)
        Spacer(modifier = Modifier.height(12.dp))
        SmallPricingSection(venueDetails)
    }
}

@Composable
fun SmallHeaderSection(venueDetails: VenueDetails?, onCopyAddressClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SmallTitleSection(venueDetails)
        Spacer(modifier = Modifier.height(8.dp))
        AddressAndPhoneSection(venueDetails, onCopyAddressClick)
        Spacer(modifier = Modifier.height(8.dp))
        SmallRatingSection(venueDetails)
    }
}

@Composable
fun SmallImageSection(
    venueDetails: VenueDetails?,
    isFavorite: Boolean,
    favoriteScale: Float,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.football_field),
            contentDescription = "Venue Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .scale(favoriteScale)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(10.dp)
                    .clickable(onClick = onFavoriteClick)
            )
        }
    }
}

@Composable
fun SmallTitleSection(venueDetails: VenueDetails?) {
    Text(
        text = venueDetails?.venueName ?: "Unknown venue",
        style = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Color(0xFF3C2E56),
            lineHeight = 24.sp,
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun AddressAndPhoneSection(details: VenueDetails?, onCopyAddressClick: () -> Unit) {
    Column {
        AddressRow(details, onCopyAddressClick)
        Spacer(modifier = Modifier.height(4.dp))
        AvailableSlots(details)
        Spacer(modifier = Modifier.height(4.dp))
        DistanceRow(details)
    }
}

@Composable
fun SmallAddressRow(details: VenueDetails?, onCopyClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_location_on_24_red),
            contentDescription = "Location",
            tint = Color(0xff949494),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = details?.address?.addressName ?: "",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Copy",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF0067FF),
                lineHeight = 18.sp,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .clickable(onClick = onCopyClick)
                .padding(start = 5.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
        )
    }
}

@Composable
fun SmallRatingSection(venueDetails: VenueDetails?) {
    Row {
        repeat(5) { index ->
            Icon(
                painter = rememberAsyncImagePainter(model = R.drawable.ic_star),
                contentDescription = "Star",
                tint = Color(0xffffb800),
                modifier = Modifier.size(16.dp)
            )
            if (index < 4) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "4.8",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF32B768),
                lineHeight = 18.sp,
            )
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "(4,981)",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            )
        )
    }
}

@Composable
fun SmallPhoneRow(venueDetails: VenueDetails?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_local_phone_24),
            contentDescription = "Phone",
            tint = Color(0xff949494),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = "+998 77 806 0278",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            )
        )
    }
}

@Composable
fun SmallPricingSection(venueDetails: VenueDetails?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${venueDetails?.pricePerHour} ${stringResource(id = R.string.som_per_hour)}",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 22.05.sp
            ),
        )
        Button(
            onClick = { /* Order action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff32b768)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(40.dp)
                .width(157.dp)
        ) {
            Text(
                text = "Order",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 16.8.sp,
                )
            )
        }
    }
}

@Preview
@Composable
private fun VenueDetailsPreview() {
}