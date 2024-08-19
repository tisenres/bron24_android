package com.bron24.bron24_android.screens.venuedetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.*
import com.bron24.bron24_android.screens.adssection.BottomIndicators
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VenueDetailsScreen(
    viewModel: VenueDetailsViewModel,
    venueId: Int,
    onBackClick: () -> Unit
) {
    LaunchedEffect(key1 = venueId) {
        viewModel.fetchVenueDetails(venueId)
    }

    val venueDetails by viewModel.venueDetails.collectAsState()
    val isLoading by remember { derivedStateOf { venueDetails == null } }

    if (isLoading) {
        LoadingScreen()
    } else {
        VenueDetailsContent(
            details = venueDetails,
            onBackClick = onBackClick,
            onShareClick = {},
            onFavoriteClick = {},
            onMapClick = {},
            onCopyAddressClick = {},
            onTakeRouteClick = {},
            onOrderClick = {}
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
fun VenueDetailsContent(
    details: VenueDetails?,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onMapClick: () -> Unit,
    onCopyAddressClick: () -> Unit,
    onTakeRouteClick: () -> Unit,
    onOrderClick: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(27.dp)
        ) {
            item {
                VenueImageSection(
                    imageUrls = details?.imageUrls ?: emptyList(),
                    onBackClick = onBackClick,
                    onShareClick = { shareVenueDetails(context, details) },
                    onFavoriteClick = onFavoriteClick
                )
            }
            item { HeaderSection(
                details,
                onMapClick,
                onCopyAddressClick = { copyAddressToClipboard(context, details?.address?.addressName) }) }
            item { InfrastructureSection(details) }
            item { DescriptionSection(details) }
            item { MapSection(details, onTakeRouteClick) }
        }

        PricingSection(
            details = details,
            onOrderClick = onOrderClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        )
    }
}

fun copyAddressToClipboard(context: Context, address: String?) {
    if (address.isNullOrBlank()) {
        Toast.makeText(context, "No address available to copy", Toast.LENGTH_SHORT).show()
        return
    }

    val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("Venue Address", address)
    clipboard?.setPrimaryClip(clip)

    Toast.makeText(context, "Address copied to clipboard", Toast.LENGTH_SHORT).show()
}

fun shareVenueDetails(context: Context, details: VenueDetails?) {
    if (details == null) {
        Toast.makeText(context, "No venue details available to share", Toast.LENGTH_SHORT).show()
        return
    }

    val shareText = buildString {
        appendLine("Check out this venue:")
        appendLine("Name: ${details.venueName}")
        appendLine("Address: ${details.address?.addressName}")
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

@Composable
fun DescriptionSection(details: VenueDetails?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        SectionTitle(text = "Additional info")
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = details?.description ?: "",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 22.sp,
                textAlign = TextAlign.Justify
            ),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Color(0xFF3C2E56),
            lineHeight = 25.sp,
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun HeaderSection(details: VenueDetails?, onMapClick: () -> Unit, onCopyAddressClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        TitleSection(details, onMapClick)
        Spacer(modifier = Modifier.height(14.dp))
        AddressAndPhoneSection(details, onCopyAddressClick)
        Spacer(modifier = Modifier.height(14.dp))
        RatingSection(details)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VenueImageSection(
    imageUrls: List<String>,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    Box(
        modifier = Modifier
            .height(206.dp)
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            VenueImage(imageUrl = imageUrls[page], page = page)
        }
        ImageOverlay(
            currentPage = pagerState.currentPage,
            totalPages = imageUrls.size,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onFavoriteClick = onFavoriteClick
        )
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
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            ClickableIconButton(
                onClick = onBackClick,
                icon = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClickableIconButton(
                    onClick = onShareClick,
                    icon = Icons.Default.Share,
                    contentDescription = "Share"
                )
                AnimatedFavoriteButton(onFavoriteClick = onFavoriteClick)
            }
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
fun ClickableIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    tint: Color = Color.Black
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp)
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
        )
    )

    IconButton(
        onClick = {
            isFavorite = !isFavorite
            onFavoriteClick()
        },
        interactionSource = interactionSource,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color.Black,
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        )
    }
}

@Composable
fun TitleSection(details: VenueDetails?, onMapClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = details?.venueName ?: "Unknown field",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 29.4.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onMapClick,
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF6F6F6))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_map_cute),
                contentDescription = "map_icon",
                colorFilter = ColorFilter.tint(Color(0xFF3DDA7E)),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
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
fun AddressRow(details: VenueDetails?, onCopyClick: () -> Unit) {
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
fun AvailableSlots(details: VenueDetails?) {
    InfoRow(
        icon = R.drawable.baseline_event_available_24,
        text = "12 available slots"
    )
}

@Composable
fun DistanceRow(details: VenueDetails?) {
    InfoRow(
        icon = R.drawable.mingcute_navigation_fill,
        text = "2.3 km from you"
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
fun RatingSection(details: VenueDetails?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Star",
                tint = Color(0xffffb800),
                modifier = Modifier.size(16.dp)
            )
            if (index < 4) Spacer(modifier = Modifier.width(4.dp))
        }
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "4.8",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
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
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            )
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "See all reviews",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF32B768),
                lineHeight = 18.sp,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InfrastructureSection(details: VenueDetails?) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Pair<String, Int>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        SectionTitle(text = "Facilities")
        Spacer(modifier = Modifier.height(15.dp))
        FacilitiesGrid(details) { text, icon ->
            selectedItem = text to icon
            showBottomSheet = true
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            InfrastructureItemDetails(selectedItem?.first, selectedItem?.second)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilitiesGrid(details: VenueDetails?, onItemClick: (String, Int) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        details?.let {
            InfrastructureItem(it.venueType, R.drawable.baseline_stadium_24, onItemClick)
            InfrastructureItem(
                "${it.peopleCapacity} players",
                R.drawable.game_icons_soccer_kick,
                onItemClick
            )
            it.infrastructure?.let { infrastructure ->
                if (infrastructure.lockerRoom) {
                    InfrastructureItem(
                        "Locker Room",
                        R.drawable.mingcute_coathanger_fill,
                        onItemClick
                    )
                }
                if (infrastructure.stands.isNotBlank()) {
                    InfrastructureItem("Stands", R.drawable.baseline_chair_24, onItemClick)
                }
                if (infrastructure.shower) {
                    InfrastructureItem("Shower", R.drawable.baseline_shower_24, onItemClick)
                }
                if (infrastructure.parking) {
                    InfrastructureItem("Parking", R.drawable.baseline_local_parking_24, onItemClick)
                }
            }
            InfrastructureItem(it.venueSurface, R.drawable.baseline_grass_24, onItemClick)
            InfrastructureItem(
                "${it.workingHoursFrom.drop(3)} - ${it.workingHoursTill.drop(3)}",
                R.drawable.baseline_access_time_filled_24,
                onItemClick
            )
        }
    }
}

@Composable
fun InfrastructureItem(text: String, iconRes: Int, onClick: (String, Int) -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) Color(0xFF32B768) else Color.White,
        animationSpec = tween(durationMillis = 300)
    )
    val textColor by animateColorAsState(
        targetValue = if (isPressed) Color.White else Color.Black,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(min = 70.dp, max = 120.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(BorderStroke(0.5.dp, Color(0xFFB8BDCA)), RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable {
                isPressed = true
                onClick(text, iconRes)
            }
            .padding(8.dp)
            .scale(scale)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = textColor,
                lineHeight = 21.sp,
            ),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            launch {
                delay(100)
                isPressed = false
            }
        }
    }
}

@Composable
fun InfrastructureItemDetails(text: String?, iconRes: Int?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 20.dp, end = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = text,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            text?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        lineHeight = 30.sp,
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Additional information about $text goes here. This can include details about the facility, its features, or any other relevant information.",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 24.sp,
            ),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun MapSection(details: VenueDetails?, onTakeRouteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.football_field),
            contentDescription = "Map",
            modifier = Modifier
                .fillMaxWidth()
                .height(109.dp)
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
            contentScale = ContentScale.Crop
        )
        MapDetails(details)
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color(0xFFD4D4D4)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onTakeRouteClick)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Take a route",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 18.sp
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Take route",
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun MapDetails(details: VenueDetails?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = details?.address?.addressName ?: "Unknown address",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 18.sp
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        DistanceInfo(
            icon = R.drawable.mingcute_navigation_fill,
            text = "8.9 km from you",
            tintColor = Color(0xFFD9D9D9),
        )
        Spacer(modifier = Modifier.height(4.dp))
        DistanceInfo(
            icon = R.drawable.ic_metro,
            text = details?.address?.closestMetroStation ?: "Unknown metro station",
            tintColor = Color(0xFFD43535),
        )
    }
}

@Composable
fun DistanceInfo(icon: Int, text: String, tintColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tintColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFFB7B3B3),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PricingSection(
    details: VenueDetails?,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color(0xFFD4D4D4)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${details?.pricePerHour} ${stringResource(id = R.string.som_per_hour)}",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 22.05.sp
                ),
            )
            Button(
                onClick = onOrderClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff32b768)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(47.dp)
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
                        letterSpacing = (-0.028).em
                    )
                )
            }
        }
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

@Preview(widthDp = 390, heightDp = 793, showBackground = true)
@Composable
private fun VenueDetailsPreview() {
    VenueDetailsContent(
        details = VenueDetails(
            venueId = 1,
            address = Address(
                id = 6,
                addressName = "Bunyodkor street, 18",
                district = "SASASAS",
                closestMetroStation = "Novza"
            ),
            city = City(id = 5, cityName = "Tashkent"),
            infrastructure = Infrastructure(
                id = 9,
                lockerRoom = true,
                stands = "FDFDFGD",
                shower = true,
                parking = true
            ),
            venueOwner = VenueOwner(
                id = 9,
                ownerName = "Owner Name",
                tinNumber = 1223243,
                contact1 = "454545",
                contact2 = "232323"
            ),
            venueName = "Bunyodkor kompleksi",
            venueType = "out",
            venueSurface = "Grass",
            peopleCapacity = 12,
            sportType = "Football",
            pricePerHour = "100",
            description = "A large stadium in Tashkent a large stadium in Tashkent a large stadium in Tashkent a large stadium in Tashkent a large stadium in Tashkent a large stadium in Tashkent a large stadium in Tashkent",
            workingHoursFrom = "9:00",
            workingHoursTill = "23:00",
            contact1 = "+998 77 806 0278",
            contact2 = "+998 77 806 0288",
            createdAt = "2021-01-01",
            updatedAt = "2023-01-01",
            imageUrls = emptyList()
        ),
        {},
        {},
        {},
        {},
        {},
        {},
        {}
    )
}

@Preview(widthDp = 390, heightDp = 793, showBackground = true)
@Composable
private fun InfrastructureItemDetailsPreview() {
    InfrastructureItemDetails(
        text = "lalalasdasasasasdfdfsdsdsdwewewewesdsdsd",
        iconRes = R.drawable.baseline_grass_24
    )
}