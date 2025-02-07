package com.bron24.bron24_android.screens.venuedetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.components.items.LoadingPlaceholder
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.helper.util.formatISODateTimeToHourString
import com.bron24.bron24_android.screens.booking.screens.startbooking.BookingScreen
import com.bron24.bron24_android.screens.booking.screens.startbooking.BookingViewModel
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import com.valentinilk.shimmer.shimmer
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.delay

data class VenueDetailsScreen(val venueId: Int) : Screen {
    @Composable
    override fun Content() {

    }

}

@Composable
fun VenueDetailsScreenContent(
    state: State<VenueDetailsContract.UIState>,
    back: () -> Unit,
    intent: (VenueOrderInfo) -> Unit
) {
//    val viewModel: VenueDetailsContract.ViewModel = hiltScreenModel()
//    remember {
//        viewModel.initData(venueId)
//    }
//    val state = viewModel.collectAsState()


    var openOrder by remember {
        mutableStateOf(false)
    }
    VenueDetailsContent(
        state = state,
        onBackClick = {
            back.invoke()
        },
        onFavoriteClick = { /* Implement favorite functionality */ },
        onMapClick = { lan, long ->
            //intent.invoke(VenueDetailsContract.Intent.ClickMap(Location(lan, long)))
        },
        onOrderClick = {
            openOrder = true
        }
    )

    if (openOrder) {
        BookingBottomSheet(
            venueId = state.value.venue?.venueId ?: 0,
            venueName = state.value.venue?.venueName ?: "",
            sectors = state.value.venue?.sectors ?: emptyList(),
            pricePerHour = state.value.venue?.pricePerHour ?: "",
            onDismiss = { openOrder = false },
        ) {
            intent.invoke(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingBottomSheet(
    venueId: Int,
    venueName: String,
    sectors: List<String>,
    pricePerHour: String,
    onDismiss: () -> Unit,
    listener: (VenueOrderInfo) -> Unit
) {
    val viewModel: BookingViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        BookingScreen(
            venueId = venueId,
            venueName = venueName,
            sectors = sectors,
            pricePerHour = pricePerHour,
            viewModel = viewModel,
            onOrderClick = { venueId, venueName, date, sector, timeSlots ->
                listener.invoke(VenueOrderInfo(venueId = venueId, venueName = venueName, date = date, sector = sector, timeSlots = timeSlots))
            },
        )
    }
}


@Composable
fun VenueDetailsContent(
    state: State<VenueDetailsContract.UIState>,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onOrderClick: () -> Unit,
    onMapClick: (Double, Double) -> Unit
) {
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    val toolbarHeight = 48.dp

    val toolbarVisible by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item(key = "imageSection") {
                if (state.value.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .shimmer()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                } else {
                    VenueImageSection(
                        imageUrls = state.value.imageUrls,
                        onBackClick = onBackClick,
                        onShareClick = { shareVenueDetails(context, state.value.venue) },
                        onFavoriteClick = onFavoriteClick
                    )
                }

            }
            item(key = "headerSection") {
                if (state.value.isLoading) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 6.dp)
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.6f)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .shimmer()
                                    .background(Color.Gray.copy(alpha = 0.2f))
                            )
                            Spacer(modifier = Modifier.weight(0.2f))
//                            Box(
//                                modifier = Modifier
//                                    .padding(horizontal = 20.dp)
//                                    .padding(bottom = 6.dp)
//                                    .size(40.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .shimmer()
//                                    .background(Color.Gray.copy(alpha = 0.2f))
//                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(0.5f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 6.dp)
                                .fillMaxWidth(0.4f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp)
                                .fillMaxWidth(0.4f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 12.dp)
                                .fillMaxWidth(0.6f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                    }
                } else {
                    HeaderSection(
                        details = state.value.venue,
                        onMapClick = onMapClick,
                        onCopyAddressClick = {
                            copyAddressToClipboard(context, state.value.venue?.address?.addressName)
                        }
                    )
                }

            }
            item(key = "infrastructureSection") {
                InfrastructureSection(
                    state = state
                )
            }
            item(key = "descriptionSection") {
                if (state.value.isLoading) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(0.6f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmer()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                } else {
                    DescriptionSection(state)
                }
            }
            item(key = "mapSection") { MapSection(state) }
        }
        AnimatedToolbar(
            visible = toolbarVisible,
            title = state.value.venue?.venueName ?: "",
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
                .zIndex(1f)
        )
        PricingSection(
            state,
            onOrderClick = onOrderClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedToolbar(
    visible: Boolean,
    title: String?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title ?: "Unknown field",
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF3C2E56),
                            lineHeight = 22.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color(0xFF3C2E56),
                navigationIconContentColor = Color(0xFF3C2E56)
            )
        )
    }
}

private fun copyAddressToClipboard(context: Context, address: String?) {
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
        appendLine("Name: ")
        appendLine("Address: ${details.address.addressName}")
        appendLine("Price: ${details.pricePerHour} per hour")
        appendLine("Working hours: ${details.workingHoursFrom} - ${details.workingHoursTill}")
        appendLine("Description: ")
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
fun DescriptionSection(state: State<VenueDetailsContract.UIState>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SectionTitle(state = state, text = stringResource(id = R.string.Additional_info))
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = state.value.venue?.description ?: "",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
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
fun SectionTitle(state: State<VenueDetailsContract.UIState>, text: String) {
    if (state.value.isLoading) {
        Box(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.2f))
        )
    } else {
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

}

@Composable
fun HeaderSection(
    details: VenueDetails?,
    onMapClick: (Double, Double) -> Unit,
    onCopyAddressClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TitleSection(details, onMapClick)
        AddressAndPhoneSection(details, onCopyAddressClick)
        RatingSection(details)
    }
}

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
            .height(230.dp)
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            VenueImage(imageUrl = imageUrls[page], page = page)
        }
        ImageOverlay(
            currentPage = pagerState.currentPage,
            totalPages = pagerState.pageCount,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Composable
fun VenueImage(imageUrl: String, page: Int) {
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(imageUrl)
//            .placeholder(R.drawable.placeholder)
//            .build()
//    )
    var isLoading by remember {
        mutableStateOf(false)
    }
    if (isLoading) {
        LoadingPlaceholder()
    }
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl, onLoading = { isLoading = true }, onSuccess = { isLoading = false }),
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ClickableIconButton(
                    onClick = onBackClick,
                    icon = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                ClickableIconButton(
                    onClick = onShareClick,
                    icon = Icons.Default.Share,
                    contentDescription = "Share"
                )
//                Spacer(modifier = Modifier.width(12.dp))
//                AnimatedFavoriteButton(onFavoriteClick = onFavoriteClick)
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
            .size(44.dp)
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
                .padding(10.dp)
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
            .size(44.dp)
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
                .padding(10.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        )
    }
}

@Composable
fun TitleSection(details: VenueDetails?, onMapClick: (Double, Double) -> Unit) {
    Row(
        modifier = Modifier
//            .padding(bottom = 14.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = details?.venueName ?: "",
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

        // Small Map Button which navigates to the YandexMapPage
//        Box(
//            modifier = Modifier
//                .size(44.dp)
//                .clip(RoundedCornerShape(10.dp))
//                .background(Color(0xFFF6F6F6))
//                .clickable {
//                    onMapClick(details?.latitude ?: 0.0, details?.longitude ?: 0.0)
//                }
//                .padding(10.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_map_cute),
//                contentDescription = "map_icon",
//                colorFilter = ColorFilter.tint(Color(0xFF3DDA7E)),
//                modifier = Modifier.fillMaxSize()
//            )
//        }
    }
}

@Composable
fun AddressAndPhoneSection(details: VenueDetails?, onCopyAddressClick: () -> Unit) {
    Column {
        AddressRow(details, onCopyAddressClick)
        //AvailableSlots(details)
        if (details?.distance?.toInt() != 0) {
            DistanceRow(details)
        }

    }
}

@Composable
fun AddressRow(details: VenueDetails?, onCopyClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
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
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(10.dp))
//                .clickable(onClick = onCopyClick)
//                .padding(
//                    horizontal = 8.dp,
//                    vertical = 6.dp,
//                )
//        ) {
//            Text(
//                text = stringResource(id = R.string.copy),
//                style = TextStyle(
//                    fontFamily = gilroyFontFamily,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 12.sp,
//                    color = Color(0xFF0067FF),
//                    lineHeight = 18.sp,
//                    textDecoration = TextDecoration.Underline
//                ),
//                modifier = Modifier.align(Alignment.Center)
//            )
        TextButton(
            onClick = {
                onCopyClick()
            },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF0067FF)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                .height(20.dp)

        ) {
            Text(
                text = stringResource(id = R.string.copy),
                fontSize = 13.sp,
                maxLines = 1,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 2.sp.toPx()
                        drawLine(
                            color = Color(0xFF0067FF),
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
            )
        }
    }
//    }
}

@Composable
fun AvailableSlots(details: VenueDetails?) {
    InfoRow(
        icon = R.drawable.baseline_event_available_24,
        text = "${details?.peopleCapacity} ${stringResource(id = R.string.available)} slot"
    )
}

@Composable
fun DistanceRow(details: VenueDetails?) {
    InfoRow(
        icon = R.drawable.mingcute_navigation_fill,
        text = String.format("%.1f", details?.distance) + " ${stringResource(id = R.string.km)}"
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
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
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
        repeat((details?.rate ?: 1).toInt()) { index ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Star",
                tint = Color(0xffffb800),
                modifier = Modifier.size(16.dp)
            )
            if (index < 4) Spacer(modifier = Modifier.width(5.dp))
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = details?.rate.toString(),
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary,
                lineHeight = 18.sp,
            )
        )
//        Spacer(modifier = Modifier.width(7.dp))
//        Text(
//            text = "(4,981)",
//            style = TextStyle(
//                fontFamily = interFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 12.sp,
//                color = Color(0xFF949494),
//                lineHeight = 18.sp,
//            )
//        )
//        Spacer(modifier = Modifier.width(7.dp))

//        Box(modifier = Modifier
//            .clip(RoundedCornerShape(10.dp))
//            .clickable {}
//            .padding(
//                horizontal = 8.dp,
//                vertical = 6.dp,
//            )
//        ) {
//            Text(
//                text = stringResource(id = R.string.see_all_reviews),
//                style = TextStyle(
//                    fontFamily = interFontFamily,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 12.sp,
//                    color = Color(0xFF32B768),
//                    lineHeight = 18.sp,
//                    textDecoration = TextDecoration.Underline
//                )
//            )
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfrastructureSection(state: State<VenueDetailsContract.UIState>) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Pair<String, Int>?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SectionTitle(state = state, text = stringResource(id = R.string.facilities))
        Spacer(modifier = Modifier.height(15.dp))
        FacilitiesGrid(state) { text, icon ->
            selectedItem = text to icon
            showBottomSheet = true
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = Color.White
        ) {
            InfrastructureItemDetails(selectedItem?.first, selectedItem?.second)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilitiesGrid(state: State<VenueDetailsContract.UIState>, onItemClick: (String, Int) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (state.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.22f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
        } else {
            state.value.venue?.let { venue ->
                InfrastructureItem(
                    venue.venueType.capitalize(),
                    null,
                    R.drawable.baseline_stadium_24,
                    onItemClick
                )
                InfrastructureItem(
                    "${venue.peopleCapacity} ${stringResource(id = R.string.players)}",
                    null,
                    R.drawable.game_icons_soccer_kick,
                    onItemClick
                )
                venue.infrastructure.forEach {
                    if (it.staticName == "locker_room") {
                        InfrastructureItem(
                            stringResource(id = R.string.changing_room),
                            it.description,
                            R.drawable.mingcute_coathanger_fill,
                            onItemClick
                        )
                    }
                    if (it.staticName == "shower") {
                        InfrastructureItem(
                            text = stringResource(id = R.string.shower),
                            info = it.description,
                            iconRes = R.drawable.baseline_shower_24,
                            onClick = onItemClick
                        )
                    }
                    if (it.staticName == "parking") {
                        InfrastructureItem(
                            text = stringResource(id = R.string.parking),
                            info = it.description,
                            iconRes = R.drawable.baseline_local_parking_24,
                            onClick = onItemClick
                        )
                    }
                }
                InfrastructureItem(
                    venue.venueSurface.capitalize(),
                    null,
                    R.drawable.baseline_grass_24,
                    onItemClick
                )
                InfrastructureItem(
                    "${venue.workingHoursFrom.formatISODateTimeToHourString()} - ${venue.workingHoursTill.formatISODateTimeToHourString()}",
                    null,
                    R.drawable.baseline_access_time_filled_24,
                    onItemClick
                )
            }
        }
    }
}

@Composable
fun InfrastructureItem(text: String, info: String? = null, iconRes: Int, onClick: (String, Int) -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) Color(0xFF32B768) else Color.White,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    val textColor by animateColorAsState(
        targetValue = if (isPressed) Color.White else Color.Black,
        animationSpec = tween(durationMillis = 300), label = ""
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
                onClick(info ?: text, iconRes)
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
                fontWeight = FontWeight.Medium,
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
            delay(100)
            isPressed = false
        }
    }
}

@Composable
fun InfrastructureItemDetails(text: String?, iconRes: Int?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
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
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            text?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        lineHeight = 34.sp,
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
fun MapSection(state: State<VenueDetailsContract.UIState>) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    DisposableEffect(lifecycleOwner) {
        val mapKit = MapKitFactory.getInstance()
        mapKit.onStart()
        mapView = MapView(context).apply { onStart() }

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    mapKit.onStart()
                    mapView?.onStart()
                }

                Lifecycle.Event.ON_STOP -> {
                    mapView?.onStop()
                    mapKit.onStop()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView?.onStop()
            mapKit.onStop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                state.value.venue?.let { venue ->
                    openMapWithOptions(context, venue.latitude, venue.longitude, venue.venueName)
                }
            }
    ) {
        if (state.value.isLoading) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.4f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }

        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                ) {
                    mapView?.let { map ->
                        AndroidView(
                            factory = { map },
                            modifier = Modifier.fillMaxSize(),
                            update = { view ->
                                state.value.venue?.let { venue ->
                                    try {
                                        val venueLocation = Point(venue.latitude, venue.longitude)
                                        view.map.move(
                                            CameraPosition(venueLocation, 15.0f, 0.0f, 0.0f),
                                            Animation(Animation.Type.SMOOTH, 0.3f),
                                            null
                                        )
                                        view.map.mapObjects.clear()

                                        val placemark = view.map.mapObjects.addPlacemark(venueLocation)
                                        val markerIcon = R.drawable.baseline_location_on_24_red
                                        val drawable = ContextCompat.getDrawable(context, markerIcon)
                                        val bitmap = drawable?.let {
                                            getBitmapFromDrawable(it, 1.5f)
                                        }
                                        placemark.setIcon(ImageProvider.fromBitmap(bitmap))

                                        // Disable user interaction with the map
                                        view.map.isScrollGesturesEnabled = false
                                        view.map.isZoomGesturesEnabled = false
                                        view.map.isTiltGesturesEnabled = false
                                        view.map.isRotateGesturesEnabled = false

                                        errorMessage = null
                                    } catch (e: Exception) {
                                        errorMessage = "Error loading map: ${e.message}"
                                    }
                                }
                            }
                        )
                    }
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                MapDetails(state.value.venue)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = Color(0xFFD4D4D4)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 12.dp)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.Open_in_maps),
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
                        contentDescription = "Open in Maps",
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
        // Clickable overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    state.value.venue?.let { venue ->
                        openMapWithOptions(
                            context,
                            venue.latitude,
                            venue.longitude,
                            venue.venueName
                        )
                    }
                }
        )
    }
}

private fun getBitmapFromDrawable(drawable: Drawable, scaleFactor: Float = 1.5f): Bitmap {
    val width = (drawable.intrinsicWidth * scaleFactor).toInt()
    val height = (drawable.intrinsicHeight * scaleFactor).toInt()
    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
        val canvas = android.graphics.Canvas(this)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
    }
}

private fun openMapWithOptions(context: Context, latitude: Double, longitude: Double, venueName: String) {
    val encodedName = Uri.encode(venueName)
    val uriString = "geo:$latitude,$longitude?q=$latitude,$longitude($encodedName)"
    val geoUri = Uri.parse(uriString)
    val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)

    val packageManager = context.packageManager
    val activities = packageManager.queryIntentActivities(mapIntent, 0)

    if (activities.isNotEmpty()) {
        val chooserIntent = Intent.createChooser(mapIntent, "Open map with")
        context.startActivity(chooserIntent)
    } else {
        // If no map apps are installed, open in browser
        val browserUri =
            Uri.parse("https://www.openstreetmap.org/?mlat=$latitude&mlon=$longitude#map=15/$latitude/$longitude")
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}

private fun navigateToMapApp(context: Context, latitude: Double, longitude: Double, venueName: String) {
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($venueName)")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        // If Google Maps is not installed, open in browser
        val browserUri =
            Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}

@Composable
private fun MapDetails(details: VenueDetails?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = details?.address?.addressName ?: "Unknown address",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                lineHeight = 18.sp
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (details?.distance?.toInt() != 0) {
            DistanceInfo(
                icon = R.drawable.mingcute_navigation_fill,
                text = String.format("%.1f ${stringResource(id = R.string.km)} ${stringResource(id = R.string.from_you)}", details?.distance ?: 0.0),
                tintColor = Color(0xFFD9D9D9),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        DistanceInfo(
            icon = R.drawable.ic_metro,
            text = details?.address?.closestMetroStation ?: "Unknown metro station",
            tintColor = Color(0xFFD43535),
        )
    }
}

@Composable
private fun DistanceInfo(icon: Int, text: String, tintColor: Color) {
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
                fontSize = 14.sp,
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
    state: State<VenueDetailsContract.UIState>,
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
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(34.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            } else {
                Text(
                    text = "${state.value.venue?.pricePerHour} ${stringResource(id = R.string.som_per_hour)}",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color(0xFF3C2E56),
                        lineHeight = 22.05.sp
                    ),
                )
            }
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier
                        .width(157.dp)
                        .height(47.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            } else {
                Button(
                    onClick = onOrderClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff32b768)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(47.dp)
                        .width(157.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.boking),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.White,
                            lineHeight = 16.8.sp,
                        )
                    )
                }
            }
        }
    }
}