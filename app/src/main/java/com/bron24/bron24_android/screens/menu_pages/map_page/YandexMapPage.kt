package com.bron24.bron24_android.screens.menu_pages.map_page

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
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
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.orbitmvi.orbit.compose.collectAsState


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
    var userLocationObject by remember { mutableStateOf<MapObject?>(null) }
    var placemarks by remember { mutableStateOf<List<MapObject>>(emptyList()) }

    val defaultLocation =
        Point(state.value.userLocation.latitude, state.value.userLocation.longitude)

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
                    CameraPosition(defaultLocation, 15f, 0f, 0f), // Corrected syntax
                    Animation(Animation.Type.LINEAR, 0f), // No animation
                    null
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            mapObjects?.let { objects ->
                // Remove old placemarks
                placemarks.forEach { placemark ->
                    (placemark.userData as? MapObjectTapListener)?.let { listener ->
                        placemark.removeTapListener(listener)
                    }
                    objects.remove(placemark)
                }
                // Update user location
                defaultLocation.let { location ->
                    val userPoint = Point(location.latitude, location.longitude)
                    userLocationObject?.let { objects.remove(it) }
                    userLocationObject = objects.addPlacemark(userPoint).apply {
                        setIcon(ImageProvider.fromResource(context, R.drawable.ic_star))
                        setIconStyle(IconStyle().setAnchor(PointF(0.5f, 0.5f)))
                    }

                    // Move camera to user location if it's the first time
                    if (view.map.cameraPosition.target == defaultLocation) {
                        view.map.move(
                            CameraPosition(userPoint, 15f, 0f, 0f),
                            Animation(Animation.Type.SMOOTH, 1f),
                            null
                        )
                    }
                }
//                val venues = listOf(VenueCoordinates(1, "denov", "40", "33"))

                // Add new venue markers
                placemarks = state.value.venueCoordinates.map { venue ->
                    val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                    val placemark = objects.addPlacemark(point)
                    val isSelected = venue.venueId == 1
                    val markerIcon = if (isSelected) {
                        R.drawable.baseline_location_on_24_red
                    } else {
                        R.drawable.baseline_location_on_24_green
                    }
                    val drawable = ContextCompat.getDrawable(context, markerIcon)
//                    val bitmap = drawable?.let {
//                        getBitmapFromDrawable(it, if (isSelected) 1.8f else 1.5f)
//                    }
                    val bitmap = drawable?.let {
                        getBitmapFromDrawable(it, if (isSelected) 1.8f else 1.5f)
                    }
                    placemark.setIcon(ImageProvider.fromBitmap(bitmap))

                    placemark.setIcon(ImageProvider.fromBitmap(bitmap))
                    placemark.userData = venue.venueId
                    val tapListener = MapObjectTapListener { mapObject, point ->
                        if (mapObject.isValid) {
                            //onMarkerClick(venue.venueId)
                            centerCameraOnMarker(view.map, point)
                            true
                        } else {
                            false
                        }
                    }
                    placemark.addTapListener(tapListener)
                    placemark
                }
            }
            view.map.addCameraListener { _, cameraPosition, _, _ ->
//                onCameraPositionChanged(cameraPosition)
            }
        }
    )

    DisposableEffect(Unit) {
        mapView?.onStart()
        onDispose {
            mapView?.onStop()
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        IconButton(onClick = {
            mapView?.map?.move(
                CameraPosition(
                    mapView?.map?.cameraPosition?.target ?: defaultLocation,
                    (mapView?.map?.cameraPosition?.zoom ?: 13f) + 1f, // Zoom in
                    0f,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }) {
            Icon(
                Icons.Outlined.Add,
                modifier = Modifier.background(Color.Black),
                contentDescription = "Zoom In"
            )
        }
        IconButton(onClick = {
            mapView?.map?.move(
                CameraPosition(
                    mapView?.map?.cameraPosition?.target ?: defaultLocation,
                    (mapView?.map?.cameraPosition?.zoom ?: 13f) - 1f, // Zoom out
                    0f,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }) {
            Icon(
                Icons.Filled.Check,
                modifier = Modifier.background(Color.Black),
                contentDescription = "Zoom Out"
            )
        }
        IconButton(onClick = {
            mapView?.map?.move(
                CameraPosition(
                    defaultLocation, // User's current location
                    15f, // Zoom level
                    0f,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }) {
            Icon(
                Icons.Default.LocationOn,
                modifier = Modifier.background(Color.Black),
                contentDescription = "Current Location"
            )
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
    LaunchedEffect(defaultLocation) {
        defaultLocation.let { location ->
            mapView?.map?.move(
                CameraPosition(
                    Point(location.latitude, location.longitude),
                    15f, 0f, 0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    }
}

fun getBitmapFromDrawable(drawable: Drawable, scaleFactor: Float = 1.5f): Bitmap {
    val width = (drawable.intrinsicWidth * scaleFactor).toInt()
    val height = (drawable.intrinsicHeight * scaleFactor).toInt()
    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
        val canvas = Canvas(this)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
    }
}

fun centerCameraOnMarker(map: Map, point: Point) {
    val currentZoom = map.cameraPosition.zoom
    val targetZoom = currentZoom.coerceAtLeast(15f) // Ensure minimum zoom level

//     Calculate a point slightly above the marker
    val offsetY = 0.002 // Adjust this value to change how much above the marker the camera centers
    val newPoint = Point(point.latitude - offsetY, point.longitude)

    map.move(
        CameraPosition(newPoint, targetZoom, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 0.5f),
        null
    )
}

@Preview(showBackground = true)
@Composable
fun YandexMapPagePreview() {
    Banner()
}


