package com.bron24.bron24_android.screens.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
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
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.delay

@Composable
fun YandexMapScreen(
    mapViewModel: VenueMapViewModel = hiltViewModel(),
    initialLatitude: Double? = null,
    initialLongitude: Double? = null
) {
    val venues by mapViewModel.venues.collectAsState()
    val currentLocation by mapViewModel.currentLocation.collectAsState()
    val venueDetails by mapViewModel.venueDetails.collectAsState()
    var selectedVenueId by remember { mutableStateOf<Int?>(null) }
    var showVenueDetails by remember { mutableStateOf(false) }
    var currentCity by remember { mutableStateOf("") }
    var markerCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(initialLatitude, initialLongitude) {
        if (initialLatitude != null && initialLongitude != null) {
            mapViewModel.centerOnCoordinates(initialLatitude, initialLongitude)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        YandexMapView(
            currentLocation = currentLocation,
            venues = venues,
            onMarkerClick = { clickedVenueId ->
                selectedVenueId = clickedVenueId
                mapViewModel.fetchVenueDetails(clickedVenueId)
                showVenueDetails = true
            },
            onCameraPositionChanged = { position ->
                currentCity = "Current City" // Replace with actual city detection logic
                markerCount = venues.size // Replace with actual visible marker count logic
            }
        )

        // City information
        Text(
            text = "Current city: $currentCity",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        // Zoom and location controls
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            IconButton(onClick = {
                mapViewModel.zoomIn()
            }) {
                Icon(Icons.Outlined.LocationOn, contentDescription = "Zoom In")
            }
            IconButton(onClick = {
                mapViewModel.zoomOut()
            }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Zoom Out")
            }
            IconButton(onClick = {
                mapViewModel.centerOnCurrentLocation(currentLocation)
            }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Current Location")
            }
        }

        // Marker count
        Text(
            text = "Visible markers: $markerCount",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )

        // Venue details
        AnimatedVisibility(
            visible = showVenueDetails,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount > 0) {
                            showVenueDetails = false
                        }
                    }
                }
        ) {
            venueDetails?.let { details ->
                SmallVenueDetailsScreen(
                    modifier = Modifier.padding(16.dp),
                    venueDetails = details
                )
            }
        }
    }
}

@Composable
fun YandexMapView(
    currentLocation: Location?,
    venues: List<VenueCoordinates>,
    onMarkerClick: (Int) -> Unit,
    onCameraPositionChanged: (CameraPosition) -> Unit
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var selectedMarkerId by remember { mutableStateOf<Int?>(null) }

    // Initialize MapKit and handle lifecycle
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
                view.map.move(
                    CameraPosition(
                        Point(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0),
                        13f, 0f, 0f
                    )
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            view.map.mapObjects.clear()

            venues.forEach { venue ->
                val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                val placemark = view.map.mapObjects.addPlacemark(point)
                val markerIcon = if (venue.venueId == selectedMarkerId) {
                    R.drawable.baseline_location_on_24_green
                } else {
                    R.drawable.baseline_location_on_24_red
                }
                val drawable = ContextCompat.getDrawable(context, markerIcon)
                val bitmap = drawable?.let {
                    getBitmapFromDrawable(
                        it,
                        if (venue.venueId == selectedMarkerId) 1.5f else 1.0f
                    )
                }
                placemark.setIcon(ImageProvider.fromBitmap(bitmap))
                placemark.addTapListener { _, _ ->
                    selectedMarkerId = venue.venueId
                    onMarkerClick(venue.venueId)
                    view.map.move(
                        CameraPosition(point, view.map.cameraPosition.zoom, 0f, 0f),
                        Animation(Animation.Type.SMOOTH, 0.3f),
                        null
                    )
                    true
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

fun getBitmapFromDrawable(drawable: Drawable, scaleFactor: Float = 1.5f): Bitmap {
    val width = (drawable.intrinsicWidth * scaleFactor).toInt()
    val height = (drawable.intrinsicHeight * scaleFactor).toInt()
    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
        val canvas = Canvas(this)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
    }
}