package com.bron24.bron24_android.screens.map

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapScreen(viewModel: VenueMapViewModel = hiltViewModel()) {
    val venues by viewModel.venues.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()
    val context = LocalContext.current

    var selectedVenue by remember { mutableStateOf<String?>(null) }

    BottomSheetScaffold(
        sheetContent = {
            selectedVenue?.let {
                Text(text = it)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val mapView = rememberMapViewWithLifecycle(context)
            AndroidView(factory = { mapView }) { mapView ->
                mapView.getMapAsync { googleMap ->
                    googleMap.uiSettings.isZoomControlsEnabled = true

                    currentLocation?.let {
                        val currentLatLng = LatLng(it.latitude, it.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13.0f))
                        addCurrentLocationMarker(googleMap, currentLatLng, context)
                    }

                    googleMap.clear()
                    venues.forEach { venue ->
                        val venueLatLng = LatLng(venue.latitude.toDouble(), venue.longitude.toDouble())
                        addVenueMarker(googleMap, venueLatLng, venue.venueName) { selectedVenue = it }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView = remember { MapView(context) }

    DisposableEffect(Unit) {
        mapView.onCreate(null)
        mapView.onStart()
        onDispose {
            mapView.onStop()
            mapView.onDestroy()
        }
    }
    return mapView
}

fun addCurrentLocationMarker(googleMap: GoogleMap, location: LatLng, context: Context) {
    googleMap.addMarker(
        MarkerOptions()
            .position(location)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
    )
}

fun addVenueMarker(
    googleMap: GoogleMap,
    location: LatLng,
    venueName: String,
    onMarkerClick: (String) -> Unit
) {
    val marker = googleMap.addMarker(
        MarkerOptions()
            .position(location)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            .title(venueName)
    )

    googleMap.setOnMarkerClickListener { clickedMarker ->
        if (clickedMarker == marker) {
            onMarkerClick(venueName)
            true
        } else {
            false
        }
    }
}