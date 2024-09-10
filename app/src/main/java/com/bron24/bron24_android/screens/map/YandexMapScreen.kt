package com.bron24.bron24_android.screens.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.mapkit.map.Map

@Composable
fun YandexMapScreen(
    mapViewModel: VenueMapViewModel = hiltViewModel(),
    initialLatitude: Double? = null,
    initialLongitude: Double? = null
) {
    val venues by mapViewModel.venues.collectAsState()
    val currentLocation by mapViewModel.currentLocation.collectAsState()
    val selectedVenueId by mapViewModel.selectedVenueId.collectAsState()
    var showVenueDetails by remember { mutableStateOf(false) }

    LaunchedEffect(initialLatitude, initialLongitude) {
        if (initialLatitude != null && initialLongitude != null) {
            mapViewModel.centerOnCoordinates(initialLatitude, initialLongitude)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        YandexMapView(
            currentLocation = currentLocation,
            venues = venues,
            selectedVenueId = selectedVenueId,
            onMarkerClick = { clickedVenueId ->
                mapViewModel.selectVenue(clickedVenueId)
                showVenueDetails = true
            },
            onCameraPositionChanged = {}
        )

        // Zoom and location controls
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            IconButton(onClick = { mapViewModel.zoomIn() }) {
                Icon(Icons.Outlined.LocationOn, contentDescription = "Zoom In")
            }
            IconButton(onClick = { mapViewModel.zoomOut() }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Zoom Out")
            }
            IconButton(onClick = { mapViewModel.centerOnCurrentLocation() }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Current Location")
            }
        }

        // Venue details
        AnimatedVisibility(
            visible = showVenueDetails,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.StartToEnd || dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        showVenueDetails = false
                        mapViewModel.clearSelectedVenue()
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
                    SmallVenueDetailsScreen(
                        viewModel = mapViewModel,
                        onClose = {
                            showVenueDetails = false
                            mapViewModel.clearSelectedVenue()
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun YandexMapView(
    currentLocation: Location?,
    venues: List<VenueCoordinates>,
    selectedVenueId: Int?,
    onMarkerClick: (Int) -> Unit,
    onCameraPositionChanged: (CameraPosition) -> Unit
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapObjects by remember { mutableStateOf<MapObjectCollection?>(null) }

    DisposableEffect(Unit) {
        MapKitFactory.initialize(context)
        mapView?.onStart()
        MapKitFactory.getInstance().onStart()

        onDispose {
            mapView?.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        factory = { context ->
            MapView(context).also { view ->
                mapView = view
                mapObjects = view.map.mapObjects.addCollection()
                view.map.move(
                    CameraPosition(
                        Point(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0),
                        13f, 0f, 0f
                    )
                )
                view.map.isZoomGesturesEnabled = true
                view.map.isRotateGesturesEnabled = false
                view.map.isFastTapEnabled = true
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            mapObjects?.clear()

            venues.forEach { venue ->
                val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                val placemark = mapObjects?.addPlacemark(point)
                val isSelected = venue.venueId == selectedVenueId
                val markerIcon = if (isSelected) {
                    R.drawable.baseline_location_on_24_red
                } else {
                    R.drawable.baseline_location_on_24_green
                }
                val drawable = ContextCompat.getDrawable(context, markerIcon)
                val bitmap = drawable?.let {
                    getBitmapFromDrawable(it, if (isSelected) 1.8f else 1.5f)
                }
                placemark?.setIcon(ImageProvider.fromBitmap(bitmap))
                placemark?.userData = venue.venueId

                placemark?.addTapListener { mapObject, _ ->
                    val clickedVenueId = mapObject.userData as? Int
                    if (clickedVenueId != null) {
                        Log.d("YandexMapView", "Marker clicked: VenueID = $clickedVenueId")
                        onMarkerClick(clickedVenueId)
                        centerCameraOnMarker(view.map, point)
                        true
                    } else {
                        false
                    }
                }
            }

            view.map.addCameraListener { _, cameraPosition, _, _ ->
                onCameraPositionChanged(cameraPosition)
            }
        }
    )

    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            mapView?.map?.move(
                CameraPosition(
                    Point(location.latitude, location.longitude),
                    13f, 0f, 0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    }
}

fun centerCameraOnMarker(map: Map, point: Point) {
    val targetPosition = map.cameraPosition.target
    val zoom = map.cameraPosition.zoom
    val azimuth = map.cameraPosition.azimuth
    val tilt = map.cameraPosition.tilt

    // Calculate a point slightly above the marker
    val offsetY = 0.002 // Adjust this value to change how much above the marker the camera centers
    val newPoint = Point(point.latitude - offsetY, point.longitude)

    map.move(
        CameraPosition(newPoint, zoom + 3, azimuth, tilt),
        Animation(Animation.Type.SMOOTH, 0.5f),
        null
    )
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