package com.bron24.bron24_android.screens.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.mapkit.map.Map
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import kotlin.math.sqrt
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun YandexMapScreen(
    mapViewModel: VenueMapViewModel = hiltViewModel(),
    initialLatitude: Double? = null,
    initialLongitude: Double? = null,
    initialSelectedVenueId: Int = -1
) {
    val venues by mapViewModel.venues.collectAsState()
    val currentLocation by mapViewModel.currentLocation.collectAsState()
    val selectedVenueId by mapViewModel.selectedVenueId.collectAsState()
    var showVenueDetails by remember { mutableStateOf(false) }

    LaunchedEffect(initialLatitude, initialLongitude, initialSelectedVenueId) {
        if (initialLatitude != null && initialLongitude != null) {
            mapViewModel.centerOnCoordinates(initialLatitude, initialLongitude)
        }
        if (initialSelectedVenueId != -1) {
            mapViewModel.selectVenue(initialSelectedVenueId)
            showVenueDetails = true
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
    var lastTappedVenueId by remember { mutableStateOf<Int?>(null) }
    var showConfetti by remember { mutableStateOf(false) }

    // Birthday coordinates
    val birthdayLocation = Point(41.294737, 69.245610)

    val debounceInterval = 500L
    val debouncedMarkerClick = remember {
        var lastClickTime = 0L
        { venueId: Int, point: Point ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceInterval && venueId != lastTappedVenueId) {
                lastClickTime = currentTime
                lastTappedVenueId = venueId
                Log.d("YandexMapView", "Marker click processed: VenueID = $venueId")
                onMarkerClick(venueId)
                mapView?.map?.let { map -> centerCameraOnMarker(map, point) }
                Handler(Looper.getMainLooper()).postDelayed({ lastTappedVenueId = null }, debounceInterval)
            } else {
                Log.d("YandexMapView", "Marker click debounced: VenueID = $venueId")
            }
        }
    }

    DisposableEffect(Unit) {
        MapKitFactory.initialize(context)
        onDispose {
            mapView?.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        factory = { context ->
            MapView(context).also { view ->
                mapView = view
                mapObjects = view.map.mapObjects
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
            }

            // Birthday marker

            val birthdayMarker = mapObjects?.addPlacemark(birthdayLocation)
            birthdayMarker?.setIcon(ImageProvider.fromResource(context, R.drawable.gift_pic))
            birthdayMarker?.setIconStyle(IconStyle().setScale(0.1f))

            birthdayMarker?.userData = "birthday"

            // Remove previous listeners to avoid duplicates
//            view.map.removeInputListener( )

            // Set up a single tap listener for the entire map
            view.map.addInputListener(object : InputListener {
                override fun onMapTap(map: Map, point: Point) {
                    val tappedVenue = findNearestVenue(point, venues, 0.001) // Adjust threshold as needed
                    tappedVenue?.let { venue ->
                        Log.d("YandexMapView", "Map tapped near venue: VenueID = ${venue.venueId}")
                        debouncedMarkerClick(venue.venueId, point)
                    }
                }

                override fun onMapLongTap(map: Map, point: Point) {
                    // Handle long tap if needed
                }
            })

            birthdayMarker?.addTapListener { _, _ ->
                // Show confetti effect
                showConfetti = true

                // Show toast
                ToastManager.showToast("HAPPY BIRTHDAY, JOXON !", ToastType.INFO)

                true
            }

            // Remove previous camera listeners to avoid duplicates
//            view.map.removeCameraListener()

            view.map.addCameraListener { _, cameraPosition, _, _ ->
                onCameraPositionChanged(cameraPosition)
            }
        }
    )

    if (showConfetti) {
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = listOf(
                Party(
                    speed = 0f,
                    maxSpeed = 30f,
                    damping = 0.9f,
                    spread = 360,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    position = Position.Relative(0.5, 0.3),
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
                )
            )
        )
        LaunchedEffect(Unit) {
            delay(5000) // Show confetti for 5 seconds
            showConfetti = false
        }
    }

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

fun findNearestVenue(point: Point, venues: List<VenueCoordinates>, threshold: Double): VenueCoordinates? {
    return venues.minByOrNull { venue ->
        val venuePoint = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
        calculateDistance(point, venuePoint)
    }?.takeIf { venue ->
        val venuePoint = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
        calculateDistance(point, venuePoint) < threshold
    }
}

fun calculateDistance(point1: Point, point2: Point): Double {
    val dx = point1.longitude - point2.longitude
    val dy = point1.latitude - point2.latitude
    return sqrt(dx * dx + dy * dy)
}

fun centerCameraOnMarker(map: Map, point: Point) {
    val currentZoom = map.cameraPosition.zoom
    val targetZoom = currentZoom.coerceAtLeast(15f) // Ensure minimum zoom level

    // Calculate a point slightly above the marker
    val offsetY = 0.002 // Adjust this value to change how much above the marker the camera centers
    val newPoint = Point(point.latitude - offsetY, point.longitude)

    map.move(
        CameraPosition(newPoint, targetZoom, 0f, 0f),
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