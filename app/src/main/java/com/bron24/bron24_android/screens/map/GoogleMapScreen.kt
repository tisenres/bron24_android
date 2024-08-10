package com.bron24.bron24_android.screens.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.*
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapScreen(
    viewModel: VenueMapViewModel = hiltViewModel(),
    venueDetailsViewModel: VenueDetailsViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()

    var selectedVenueId by remember { mutableStateOf<Int?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

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
        currentLocation?.let { location ->
            CustomMarker(
                position = LatLng(location.latitude, location.longitude),
                title = "Current Location",
                iconResourceId = R.drawable.baseline_location_on_24_red
            )
        }

        venues.forEach { venue ->
            CustomMarker(
                position = LatLng(venue.latitude.toDouble(), venue.longitude.toDouble()),
                title = venue.venueName,
                iconResourceId = R.drawable.baseline_location_on_24_green,
                onMarkerClick = { onMarkerClick(9) }
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

}