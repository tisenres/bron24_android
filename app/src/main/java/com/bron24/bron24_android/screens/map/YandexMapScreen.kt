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
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

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
            SmallVenueDetailsScreen(
                modifier = Modifier.padding(16.dp),
                venueId = selectedVenueId ?: 0,
                viewModel = mapViewModel,
                onClose = { mapViewModel.clearSelectedVenue() }
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
                val isSelected = venue.venueId == selectedVenueId
                val markerIcon = if (isSelected) {
                    R.drawable.baseline_location_on_24_green
                } else {
                    R.drawable.baseline_location_on_24_red
                }
                val drawable = ContextCompat.getDrawable(context, markerIcon)
                val bitmap = drawable?.let {
                    getBitmapFromDrawable(it, if (isSelected) 1.5f else 1.0f)
                }
                placemark.setIcon(ImageProvider.fromBitmap(bitmap))
                placemark.addTapListener { _, _ ->
                    onMarkerClick(venue.venueId)
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