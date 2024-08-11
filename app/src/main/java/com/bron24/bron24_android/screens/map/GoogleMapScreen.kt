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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.*
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.core.content.res.ResourcesCompat
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapScreen(
    viewModel: VenueMapViewModel = hiltViewModel(),
    venueDetailsViewModel: VenueDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val venues by viewModel.venues.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()
    var selectedVenueId by remember { mutableStateOf<Int?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    // Create a launcher to request permissions
    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        if (coarseLocationGranted || fineLocationGranted) {
            // Permissions granted, proceed to get the location
            viewModel.updateCurrentLocation()
        } else {
            // Handle the case where permissions are denied
            // Show an appropriate message to the user or disable certain features
        }
    }

    // Check if the permissions are already granted
    LaunchedEffect(Unit) {
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED ||
            fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            // Request permissions
            requestPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        } else {
            // Permissions already granted, proceed to get the location
            viewModel.updateCurrentLocation()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapView(
            currentLocation = currentLocation,
            venues = venues,
            onMarkerClick = { clickedVenueId -> selectedVenueId = clickedVenueId }
        )

        selectedVenueId?.let { venueId ->
            ModalBottomSheet(
                onDismissRequest = { selectedVenueId = null },
                sheetState = bottomSheetState
            ) {
                VenueDetailsScreen(
                    viewModel = venueDetailsViewModel,
                    venueId = venueId,
                    onDismiss = { selectedVenueId = null }
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
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0),
            13f
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {

        venues.forEach { venue ->
            CustomMarker(
                position = LatLng(venue.latitude.toDouble(), venue.longitude.toDouble()),
                title = venue.venueName,
                iconResourceId = R.drawable.baseline_location_pin_24,
                onMarkerClick = { onMarkerClick(9) } // Assuming venue.id is the identifier
            )
        }
    }
}

@Composable
fun CustomMarker(
    position: LatLng,
    title: String,
    iconResourceId: Int,
    onMarkerClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val bitmapDescriptor = remember(iconResourceId) {
        val drawable = ResourcesCompat.getDrawable(context.resources, iconResourceId, null)
        drawable?.let {
            getBitmapFromDrawable(it)
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