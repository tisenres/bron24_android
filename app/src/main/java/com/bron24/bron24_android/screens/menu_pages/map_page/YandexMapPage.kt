package com.bron24.bron24_android.screens.menu_pages.map_page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
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
import androidx.compose.material3.SwipeToDismissBox
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
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.orbitmvi.orbit.compose.collectAsState

private const val MAX_ZOOM_LEVEL = 20f
private const val MIN_ZOOM_LEVEL = 5f

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
fun Banner() {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                val originalBitmap = BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.kids_playing_soccer_cartoon
                )
                val resizedBitmap =
                    Bitmap.createScaledBitmap(originalBitmap, 100, 100, true) // 100x100 o‘lcham
                val imageProvider = ImageProvider.fromBitmap(resizedBitmap)

                map.move(
                    CameraPosition(
                        Point(41.2995, 69.2401), // Toshkent koordinatalari
                        12.0f, // Zoom darajasi
                        0.0f, // Tilt
                        0.0f  // Azimuth
                    )
                )
                map.mapObjects.addPlacemark(
                    Point(41.2995, 69.2401), // Marker koordinatalari,
                    imageProvider
                )
                map.isZoomGesturesEnabled = true

            }
        },
        update = { mapView ->
            // Xarita parametrlarini o'zgartirish yoki yangilash uchun
        }
    )
}

@Composable
fun YandexMapPageContent(
    state: State<YandexMapPageContract.UIState>,
    intent: (YandexMapPageContract.Intent) -> Unit
) {
    val showVenueDetails by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapObjects by remember { mutableStateOf<MapObjectCollection?>(null) }
//    var userLocationObject by remember { mutableStateOf<MapObject?>(null) }
//    var placemarks by remember { mutableStateOf<List<MapObject>>(emptyList()) }
//    var placemarksNew by remember { mutableStateOf<List<PlacemarkMapObject>>(emptyList()) }

//    val userLocation by remember {
//        mutableStateOf(
//            value = Point(state.value.userLocation.latitude, state.value.userLocation.longitude)
//        )
//    }

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
                        mapView!!,
                        mapObjects!!,
                        userLocation,
                        context
                    )
                    state.value.venueCoordinates.map { venue ->
                        val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                        setStadiumMarker(
                            mapView!!,
                            mapObjects!!,
                            point,
                            context
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                setMarkerInStartLocation(
                    mapView!!,
                    mapObjects!!,
                    userLocation,
                    context
                )
                state.value.venueCoordinates.map { venue ->
                    val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                    setStadiumMarker(
                        mapView!!,
                        mapObjects!!,
                        point,
                        context
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

        // Venue details
        AnimatedVisibility(
            visible = showVenueDetails,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
        ) {
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.StartToEnd || dismissValue == SwipeToDismissBoxValue.EndToStart) {
//                        showVenueDetails = false
//                        mapViewModel.clearSelectedVenue()
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = { /* Optional background content */ },
                content = {
//                    SmallVenueDetailsScreen(
//                        viewModel = mapViewModel,
//                        onClose = {
//                            showVenueDetails = false
//                            mapViewModel.clearSelectedVenue()
//                        }
//                    )
                }
            )
        }
        LaunchedEffect(userLocation) {
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
                            15f,
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

private fun setMarkerInStartLocation(mapView: MapView, mapObjects: MapObjectCollection, location: Point, context: Context) {
    val originalBitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.location_red
    )
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 300, 300, true)
    val imageProvider = ImageProvider.fromBitmap(resizedBitmap)

    val placemark = mapObjects.addPlacemark(
        location,
        imageProvider,
        IconStyle().apply {
            scale = 0.5f
        }
    )

    placemark.addTapListener { _, _ ->
        centerCameraOnMarker(mapView, location)
        Toast.makeText(context, "Marker clicked at: ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
        true
    }
}

fun setStadiumMarker(mapView: MapView, mapObjects: MapObjectCollection, point: Point, context: Context) {
    val originalBitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.location_green
    )
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 300, 300, true)
    val imageProvider = ImageProvider.fromBitmap(resizedBitmap)

    val placemark = mapObjects.addPlacemark(
        point,
        imageProvider,
        IconStyle().apply {
            scale = 0.5f
        }
    )

    placemark.addTapListener { _, _ ->

        centerCameraOnMarker(mapView, point)
        Toast.makeText(context, "Marker clicked at: ${point.latitude}, ${point.longitude}", Toast.LENGTH_SHORT).show()
        true
    }
}


fun centerCameraOnMarker(mapView: MapView, point: Point) {
    val offsetY = 0.002
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


