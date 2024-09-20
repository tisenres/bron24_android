package com.bron24.bron24_android.screens.venuelisting

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import com.bron24.bron24_android.screens.main.Screen
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.valentinilk.shimmer.shimmer

@Composable
fun VenueCard(venue: Venue? = null, isLoading: Boolean, navController: NavController) {
    val navigateToDetails: (() -> Unit)? = remember(venue) {
        venue?.venueId?.let { id ->
            { navController.navigate(Screen.VenueDetails.route.replace("{venueId}", id.toString())) }
        }
    }

    val onFavoriteClick: () -> Unit = {}

    val shimmerModifier = if (isLoading) {
        Modifier.shimmer()
    } else {
        Modifier
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF4FEF4).copy(alpha = 0.47f))
            .then(shimmerModifier)
            .let {
                if (!isLoading) {
                    navigateToDetails?.let { action ->
                        it.clickable(onClick = action)
                    } ?: it
                } else {
                    it
                }
            }
    ) {
        if (isLoading) {
            LoadingPlaceholder()
        } else if (venue != null) {
            VenueImageSection(venue, onFavoriteClick)
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
            .padding(bottom = 20.dp)
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
fun VenueImageSection(venue: Venue, onFavoriteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(162.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(venue.imageUrl ?: "")
                .scale(Scale.FILL)
                .crossfade(true)
                .build(),
            contentDescription = "Venue Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        AnimatedFavoriteButton(
            onFavoriteClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(5.dp)
        )
    }
}

@Composable
fun AnimatedFavoriteButton(onFavoriteClick: () -> Unit, modifier: Modifier = Modifier) {
    var isFavorite by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Box(
        modifier = modifier
            .size(40.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    isFavorite = !isFavorite
                    onFavoriteClick()
                    ToastManager.showToast(
                        "Venue was added to favorites",
                        ToastType.SUCCESS
                    )
                }
            )
    ) {
        Image(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.Favorite,
            contentDescription = "Favorite",
            colorFilter = ColorFilter.tint(if (isFavorite) Color.Red else Color.White),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        )
    }
}

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
                text = "4.5",
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
                        .size(14.dp)
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
                    text = "12 " + stringResource(id = R.string.slots),
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

@Composable
@Preview(showBackground = true)
fun PreviewVenueCard() {
//    val venue = Venue(
//        venueId = 5,
//        venueName = "Test name LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG",
//        pricePerHour = "800000",
//        address = Address(
//            id = 6,
//            addressName = "Bunyodkor  LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONkochasi",
//            district = "Chilonzor",
//            closestMetroStation = "Novza",
//        ),
//        imageUrl = null,
//        distance = 0.0
//    )
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dollar),
            contentDescription = "Price Icon",
            modifier = Modifier.size(14.dp).align(Alignment.CenterVertically)
        )
        Text(
            text = "234545" + stringResource(id = R.string.som_per_hour),
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
}