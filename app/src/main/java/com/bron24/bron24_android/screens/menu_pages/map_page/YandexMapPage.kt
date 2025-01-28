package com.bron24.bron24_android.screens.menu_pages.map_page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.orbitmvi.orbit.compose.collectAsState

private const val MAX_ZOOM_LEVEL = 20f
private const val MIN_ZOOM_LEVEL = 5f

private const val TAG = "YandexMapPage"

object YandexMapPage : Tab {
    private fun readResolve(): Any = YandexMapPage
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.nearby)
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_map))
            return TabOptions(
                index = 2u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        val viewModel: YandexMapPageContract.ViewModel = getViewModel<YandexMapPageVM>()
        remember {
            viewModel.initData()
        }
        val uiState = viewModel.collectAsState()
        YandexMapPageContent(uiState, viewModel::onDispatchers)
    }
}

@Composable
fun YandexMapPageContent(
    state: State<YandexMapPageContract.UIState>,
    intent: (YandexMapPageContract.Intent) -> Unit
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapObjects by remember { mutableStateOf<MapObjectCollection?>(null) }
    val mapObjectListeners by remember { mutableStateOf<MutableMap<Point, MapObjectTapListener>>(mutableMapOf()) }
    val firstUpdateTime = remember { mutableStateOf<Long>(0L) }

    val userLocation = state.value.userLocation.let {
        Point(it.latitude, it.longitude)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context).also { view ->
                    mapView = view
                    mapObjects = view.map.mapObjects

                    view.map.isZoomGesturesEnabled = true
                    view.map.isRotateGesturesEnabled = true
                    view.map.isTiltGesturesEnabled = true
                    view.map.isScrollGesturesEnabled = true

                    view.map.move(
                        CameraPosition(userLocation, 15f, 0f, 0f)
                    )

                    setMarkerInStartLocation(
                        mapObjects!!,
                        userLocation,
                        context,
                    )
                    state.value.venueCoordinates.map { venue ->
                        val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                        setStadiumMarker(
                            mapView!!,
                            mapObjects!!,
                            (point to venue),
                            context,
                            intent,
                            mapObjectListeners
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                setMarkerInStartLocation(
                    mapObjects!!,
                    userLocation,
                    context,
                )
                state.value.venueCoordinates.map { venue ->
                    val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                    setStadiumMarker(
                        mapView!!,
                        mapObjects!!,
                        (point to venue),
                        context,
                        intent,
                        mapObjectListeners
                    )
                }
            }
        )

        ZoomControls(
            modifier = Modifier.align(Alignment.TopEnd),
            mapView = mapView,
            userLocation = userLocation
        )

        DisposableEffect(Unit) {
            mapView?.onStart()
            onDispose {
                mapView?.onStop()
                mapView?.map?.mapObjects?.clear()
                mapView = null
            }
        }

        AnimatedVisibility(
            visible = state.value.venueDetails != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {

            val venueDetails = state.value.venueDetails
            if (venueDetails != null) {
                MapVenueDetails(
                    venueDetails = venueDetails,
                    modifier = Modifier
                ) {}
            }

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.StartToEnd || dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        //                       mapViewModel.clearSelectedVenue()
                        true
                    } else {
                        false
                    }
                }
            )
//
//            SwipeToDismissBox(
//                state = dismissState,
//                backgroundContent = { /* Optional background content */ },
//                content = {
//                    SmallVenueDetailsScreen(
//                        viewModel = mapViewModel,
//                        onClose = {
//                            showVenueDetails = false
//                            mapViewModel.clearSelectedVenue()
//                        }
//                    )
//                }
//            )
//        }
        }

        LaunchedEffect(userLocation) {
            if (firstUpdateTime.value == 0L) {
                firstUpdateTime.value = System.currentTimeMillis()
            }
            if (System.currentTimeMillis() - firstUpdateTime.value <= 3000L) {
                userLocation.let { location ->
                    mapView?.map?.move(
                        CameraPosition(
                            Point(location.latitude, location.longitude),
                            15f, 0f, 0f
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun ZoomControls(modifier: Modifier, mapView: MapView?, userLocation: Point) {
    Column(
        modifier = modifier
            .padding(top = 28.dp, end = 14.dp),
        horizontalAlignment = Alignment.End
    ) {

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .size(60.dp)
                .clickable {
                    val currentZoom = mapView?.map?.cameraPosition?.zoom ?: 13f
                    if (currentZoom < MAX_ZOOM_LEVEL) {
                        mapView?.map?.move(
                            CameraPosition(
                                mapView.map?.cameraPosition?.target ?: userLocation,
                                currentZoom + 1f,
                                0f,
                                0f
                            ),
                            Animation(Animation.Type.SMOOTH, 0.5f),
                            null
                        )
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Zoom Out",
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .size(60.dp)
                .clickable {
                    mapView?.map?.move(
                        CameraPosition(
                            mapView.map?.cameraPosition?.target ?: userLocation,
                            (mapView.map?.cameraPosition?.zoom ?: 13f) - 1f,
                            0f,
                            0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0.5f),
                        null
                    )
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Zoom Out",
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(120.dp))

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .size(60.dp)
                .clickable {
                    mapView?.map?.move(
                        CameraPosition(
                            userLocation,
                            14f,
                            0f,
                            0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0.5f),
                        null
                    )
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.near_me),
                contentDescription = "Zoom Out",
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            )
        }
    }
}

private fun setMarkerInStartLocation(
    mapObjects: MapObjectCollection,
    location: Point,
    context: Context
) {
    val marker = createBitmapFromVector(R.drawable.location_pin_svg, context)
    mapObjects.addPlacemark(
        location,
        ImageProvider.fromBitmap(marker)
    )
}

fun setStadiumMarker(
    mapView: MapView,
    mapObjects: MapObjectCollection,
    point: Pair<Point, VenueCoordinates>,
    context: Context,
    intent: (YandexMapPageContract.Intent) -> Unit,
    mapObjectListeners: MutableMap<Point, MapObjectTapListener> = mutableMapOf()
) {
    val marker = createBitmapFromVector(R.drawable.green_location_pin, context)

    val placemark = mapObjects.addPlacemark(
        point.first,
        ImageProvider.fromBitmap(marker)
    )

    // Only add listener if one doesn't exist for this point
    if (!mapObjectListeners.containsKey(point.first)) {
        val listener = MapObjectTapListener { _, _ ->
            centerCameraOnMarker(mapView, point.first)
            updateMarkerAppearance(placemark, context, isHighlighted = true)

            placemark.useAnimation().play()
            intent.invoke(YandexMapPageContract.Intent.ClickMarker(point.second))
            true
        }

        mapObjectListeners[point.first] = listener
        placemark.addTapListener(listener)
    }
}

fun updateMarkerAppearance(placemark: PlacemarkMapObject, context: Context, isHighlighted: Boolean) {
    if (isHighlighted) {
        placemark.setIcon(ImageProvider.fromResource(context, R.drawable.red_location_pin))
        placemark.setIconStyle(
            IconStyle().apply {
                scale = 2f
            }
        )
    } else {
        placemark.setIcon(ImageProvider.fromResource(context, R.drawable.green_location_pin))
        placemark.setIconStyle(
            IconStyle().apply {
                scale = 1f
            }
        )
    }
}

private fun createBitmapFromVector(art: Int, context: Context): Bitmap? {
    val drawable = ContextCompat.getDrawable(context, art) ?: return null
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun centerCameraOnMarker(mapView: MapView, point: Point) {
    val offsetY = 0.004
    val newPoint = Point(point.latitude - offsetY, point.longitude)

    mapView.map.move(
        CameraPosition(newPoint, 15f, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 0.5f),
        null
    )
}

@Preview
@Composable
fun YandexMapPagePreview() {
}


