package com.bron24.bron24_android.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.geometry.Point
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates

@Composable
fun YandexMapScreen(viewModel: VenueMapViewModel = hiltViewModel()) {
    val venues by viewModel.venues.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()
//    val locationPermissionState by viewModel.locationPermissionState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        val mapView = rememberMapViewWithLifecycle()
        AndroidView(factory = { mapView }) { mapView ->
            currentLocation?.let {
                mapView.map.move(CameraPosition(Point(it.latitude, it.longitude), 14.0f, 0.0f, 0.0f))
                addCurrentLocationMarker(mapView, it)
            }
            venues.forEach { venue ->
                addVenueMarker(mapView, venue)
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    DisposableEffect(Unit) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }
    return mapView
}

fun addCurrentLocationMarker(mapView: MapView, location: Location) {
    mapView.map.mapObjects.addPlacemark(Point(location.latitude, location.longitude))
}

fun addVenueMarker(mapView: MapView, venue: VenueCoordinates) {
    mapView.map.mapObjects.addPlacemark(Point(venue.latitude.toDouble(), venue.longitude.toDouble()))
}
