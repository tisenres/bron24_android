package com.bron24.bron24_android.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.*
import com.bron24.bron24_android.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.screens.venuedetails.SmallVenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.*

@Composable
fun GoogleMapScreen(
    viewModel: VenueMapViewModel = hiltViewModel(),
    venueDetailsViewModel: VenueDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val venues by viewModel.venues.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()
    var selectedVenueId by remember { mutableStateOf<Int?>(null) }

    // Request location permissions and get the current location
    RequestLocationPermissionsAndFetchLocation(viewModel)

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapView(
            currentLocation = currentLocation,
            venues = venues,
            onMarkerClick = { clickedVenueId -> selectedVenueId = clickedVenueId }
        )

        selectedVenueId?.let { venueId ->
            // Overlay the venue details at the bottom
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 27.dp, start = 16.dp, end = 16.dp)
            ) {
                SmallVenueDetailsScreen(
                    // Pass the necessary parameters here
                )
            }
        }
    }
}

@Composable
fun GoogleMapView(
    currentLocation: Location?,
    venues: List<VenueCoordinates>,
    onMarkerClick: (Int) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState()
    var selectedVenueId by remember { mutableStateOf<Int?>(null) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        venues.forEach { venue ->
            val isSelected = selectedVenueId == 9

            CustomMarker(
                position = LatLng(venue.latitude.toDouble(), venue.longitude.toDouble()),
                title = venue.venueName,
                iconResourceId = if (isSelected) R.drawable.baseline_location_on_24_green else R.drawable.baseline_location_on_24_red,
                scaleFactor = if (isSelected) 1.5f else 1.0f,
                onMarkerClick = {
                    selectedVenueId = 9
                    onMarkerClick(9)
                }
            )
        }
    }

    // Smoothly animate camera to the selected marker
    LaunchedEffect(selectedVenueId) {
        val selectedVenue = venues.find { 9 == selectedVenueId }
        selectedVenue?.let { venue ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(venue.latitude.toDouble(), venue.longitude.toDouble()),
                    15f
                )
            )
        }
    }

    // Update the camera position when the current location changes
    LaunchedEffect(currentLocation) {
        if (currentLocation != null) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(currentLocation.latitude, currentLocation.longitude),
                13f
            )
        }
    }
}

@Composable
fun RequestLocationPermissionsAndFetchLocation(viewModel: VenueMapViewModel) {
    val context = LocalContext.current

    // Create a launcher to request permissions
    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        if (coarseLocationGranted || fineLocationGranted) {
            viewModel.updateCurrentLocation()
        }
    }

    LaunchedEffect(Unit) {
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED ||
            fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        } else {
            viewModel.updateCurrentLocation()
        }
    }
}

@Composable
fun CustomMarker(
    position: LatLng,
    title: String,
    iconResourceId: Int,
    scaleFactor: Float = 1.0f,
    onMarkerClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val bitmapDescriptor = remember(iconResourceId, scaleFactor) {
        val drawable = ResourcesCompat.getDrawable(context.resources, iconResourceId, null)
        drawable?.let {
            getBitmapFromDrawable(it, scaleFactor)
        }?.let {
            BitmapDescriptorFactory.fromBitmap(it)
        }
    }

    Marker(
        position = position,
        title = title,
        icon = bitmapDescriptor,  // Set the custom icon here
        onClick = {
            onMarkerClick()
            true
        }
    )
}

fun getBitmapFromDrawable(drawable: Drawable, scaleFactor: Float = 1.5f): Bitmap {
    // Calculate the scaled width and height
    val width = (drawable.intrinsicWidth * scaleFactor).toInt()
    val height = (drawable.intrinsicHeight * scaleFactor).toInt()

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}