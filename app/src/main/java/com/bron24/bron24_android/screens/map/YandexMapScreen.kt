package com.bron24.bron24_android.screens.map

import android.content.Context
import android.graphics.PointF
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
import com.bron24.bron24_android.R
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.geometry.Point
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.image.AnimatedImageProvider
import com.yandex.runtime.image.ImageProvider
import kotlin.contracts.contract

@Composable
fun YandexMapScreen(viewModel: VenueMapViewModel = hiltViewModel()) {
    val venues by viewModel.venues.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()
    val context = LocalContext.current
//    val locationPermissionState by viewModel.locationPermissionState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        val mapView = rememberMapViewWithLifecycle(context)
        AndroidView(factory = { mapView }) { mapView ->
            currentLocation?.let {
                mapView.map.move(
                    CameraPosition(
                        Point(it.latitude, it.longitude),
                        11.0f,
                        0.0f,
                        0.0f
                    )
                )
                addCurrentLocationMarker(context, mapView, it)
            }
            venues.forEach { venue ->
                addVenueMarker(context, mapView, venue)
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
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

fun addCurrentLocationMarker(context: Context, mapView: MapView, location: Location) {
    val placemark = mapView.map.mapObjects.addPlacemark().apply {
        geometry = Point(59.935493, 30.327392)
        setIcon(ImageProvider.fromResource(context, R.drawable.baseline_location_on_24_red))
    }

}

fun addVenueMarker(context: Context, mapView: MapView, venue: VenueCoordinates) {
    mapView.map.mapObjects.addPlacemark(
        Point(
            venue.latitude.toDouble(),
            venue.longitude.toDouble()
        )
    )
}
