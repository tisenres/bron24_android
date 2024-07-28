package com.bron24.bron24_android.screens.map

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun YandexMapScreen(viewModel: VenueMapViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val venues by viewModel.venues.collectAsState(initial = emptyList())
    val currentLocation by viewModel.currentLocation.collectAsState(initial = null)
    val locationPermissionState by viewModel.locationPermissionState.collectAsState()

    DisposableEffect(Unit) {
        MapKitFactory.initialize(context)
        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            MapView(ctx).apply {
                lifecycleOwner.lifecycle.addObserver(
                    MapViewLifecycleObserver(this)
                )
            }
        },
        update = { mapView ->
            mapView.map.mapObjects.clear()

            venues.forEach { venue ->
                val point = Point(venue.address.latitude, venue.address.longitude)
                val placemark = mapView.map.mapObjects.addPlacemark(point)
                placemark.setIcon(ImageProvider.fromResource(context, R.drawable.joxon_pic))
                placemark.userData = venue

                placemark.addTapListener { mapObject, _ ->
                    val tappedVenue = mapObject.userData as? Venue
                    tappedVenue?.let {
                        viewModel.onVenueTapped(it)
                    }
                    true
                }
            }

            currentLocation?.let { location ->
                val currentLocationPoint = Point(location.latitude, location.longitude)
                val currentLocationPlacemark = mapView.map.mapObjects.addPlacemark(currentLocationPoint)
                currentLocationPlacemark.setIcon(ImageProvider.fromResource(context, R.drawable.ronaldo))

                // Move camera to the current location
                mapView.map.move(
                    CameraPosition(currentLocationPoint, 15.0f, 0.0f, 0.0f)
                )
            }
        }
    )

    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState == LocationPermissionState.DENIED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else if (locationPermissionState == LocationPermissionState.GRANTED) {
            viewModel.updateCurrentLocation()
        }
    }
}

const val LOCATION_PERMISSION_REQUEST_CODE = 1000

class MapViewLifecycleObserver(
    private val mapView: MapView
) : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop(owner: LifecycleOwner) {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }
}