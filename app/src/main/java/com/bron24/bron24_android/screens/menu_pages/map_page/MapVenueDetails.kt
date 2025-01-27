package com.bron24.bron24_android.screens.menu_pages.map_page

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import com.valentinilk.shimmer.shimmer

@Composable
fun MapVenueDetails(
    venueDetails: VenueDetails,
    modifier: Modifier,
    onOrderPressed: () -> Unit,
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }
    val isLoading by remember { derivedStateOf { false } }

    if (isLoading) {
        LoadingScreen(modifier)
    } else {
        SmallDetailsContent(
            modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            venueDetails = venueDetails,
            onFavoriteClick = { isFavorite = !isFavorite },
            onCopyAddressClick = {
                copyAddressToClipboard(
                    context,
                    venueDetails.address.addressName
                )
            },
            onOrderPressed = onOrderPressed
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .shimmer()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color.LightGray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.LightGray)
                )
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Composable
fun SmallDetailsContent(
    modifier: Modifier,
    venueDetails: VenueDetails?,
    onFavoriteClick: () -> Unit,
    onCopyAddressClick: () -> Unit,
    onOrderPressed: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        SmallImageSection(
            imageUrls = listOf(
                venueDetails?.imageUrl
                    ?: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.reformsports.com%2Fwhy-do-they-sprinkle-football-pitches%2F&psig=AOvVaw0ySGjgQox6UGKgtGCoY45Z&ust=1737820026401000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIj70JzajosDFQAAAAAdAAAAABAE",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.reformsports.com%2Fwhy-do-they-sprinkle-football-pitches%2F&psig=AOvVaw0ySGjgQox6UGKgtGCoY45Z&ust=1737820026401000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIj70JzajosDFQAAAAAdAAAAABAE"
            ),
            onShareClick = { shareVenueDetails(context, venueDetails) },
            onFavoriteClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        SmallHeaderSection(venueDetails, onCopyAddressClick)
        Spacer(modifier = Modifier.height(12.dp))
        SmallPricingSection(venueDetails, onOrderPressed)
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
fun VenueImage(imageUrl: String, page: Int) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .placeholder(R.drawable.placeholder)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = "Venue Image $page",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ImageOverlay(
    currentPage: Int,
    totalPages: Int,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableIconButton(
                onClick = onShareClick,
                icon = Icons.Default.Share,
                contentDescription = "Share"
            )
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedFavoriteButton(onFavoriteClick = onFavoriteClick)
        }
        BottomIndicators(
            currentPage = currentPage,
            totalPages = totalPages,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 11.5.dp)
        )
    }
}

@Composable
fun BottomIndicators(currentPage: Int, totalPages: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .height(1.5.dp)
                    .padding(end = 3.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .width(28.dp)
                    .background(if (index == currentPage) Color.White else Color(0xFFB7B3B3))
            )
        }
    }
}

@Composable
fun ClickableIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    tint: Color = Color.Black
) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFFFFF))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = onClick
            )
    ) {
        Image(
            imageVector = icon,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Composable
fun AnimatedFavoriteButton(onFavoriteClick: () -> Unit) {
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
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color.White)
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
            ),
    ) {
        Image(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Favorite",
            colorFilter = ColorFilter.tint(if (isFavorite) Color.Red else Color.Black),
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
fun SmallImageSection(
    imageUrls: List<String>,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {

    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            VenueImage(
                imageUrl = imageUrls[page],
                page = page
            )
        }
        ImageOverlay(
            currentPage = pagerState.currentPage,
            totalPages = imageUrls.size,
            onShareClick = onShareClick,
            onFavoriteClick = onFavoriteClick
        )
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
        SmallAddressRow(details, onCopyAddressClick)
        Spacer(modifier = Modifier.height(4.dp))
        AvailableSlots(details)
        Spacer(modifier = Modifier.height(4.dp))
        DistanceRow(details)
    }
}

@Composable
fun AvailableSlots(details: VenueDetails?) {
    InfoRow(
        icon = R.drawable.baseline_event_available_24,
        text = details?.slots.toString() + " " + stringResource(id = R.string.hours),
    )
}

@Composable
fun DistanceRow(details: VenueDetails?) {
    InfoRow(
        icon = R.drawable.mingcute_navigation_fill,
        text = String.format("%.1f km ${stringResource(id = R.string.from_you )}",details?.distance?:0.0),
    )
}

@Composable
fun InfoRow(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xff949494),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
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
fun SmallPricingSection(
    venueDetails: VenueDetails?,
    onOrderPressed: () -> Unit
) {
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
            onClick = { onOrderPressed() },
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

fun copyAddressToClipboard(context: Context, address: String?) {
    if (address.isNullOrBlank()) {
        ToastManager.showToast(
            "No address available to copy",
            ToastType.WARNING
        )
        return
    }

    val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("Venue Address", address)
    clipboard?.setPrimaryClip(clip)

    ToastManager.showToast(
        "Address copied to clipboard",
        ToastType.SUCCESS
    )
}

fun shareVenueDetails(context: Context, details: VenueDetails?) {
    if (details == null) {
        ToastManager.showToast(
            "No venue details available to share",
            ToastType.WARNING
        )
        return
    }

    val shareText = buildString {
        appendLine("Check out this venue:")
        appendLine("Name: ${details.venueName}")
        appendLine("Address: ${details.address.addressName}")
        appendLine("Price: ${details.pricePerHour} per hour")
        appendLine("Working hours: ${details.workingHoursFrom} - ${details.workingHoursTill}")
        appendLine("Description: ${details.description}")
    }

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

@Preview
@Composable
private fun VenueDetailsPreview() {
}