package com.bron24.bron24_android.screens.map

import android.Manifest
import android.app.Activity
import android.graphics.PointF
import android.util.Log
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
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
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
                // Center the camera on Tashkent initially
                map.move(
                    CameraPosition(Point(41.271970, 69.191573), 6.0f, 150.0f, 30.0f)
                )
            }
        },
        update = { mapView ->
            mapView.map.mapObjects.clear()

            val placemark = mapView.map.mapObjects.addPlacemark().apply {
                geometry = Point(41.271970, 69.191573)
                setText(
                    "Special place",
                    TextStyle().apply {
                        size = 10f
                        placement = TextStyle.Placement.RIGHT
                        offset = 5f
                    },
                )
            }

            placemark.useCompositeIcon().apply {
                setIcon(
                    "pin",
                    ImageProvider.fromResource(context, R.drawable.ic_dollar),
                    IconStyle().apply {
                        anchor = PointF(0.5f, 1.0f)
                        scale = 0.9f
                    }
                )
                setIcon(
                    "point",
                    ImageProvider.fromResource(context, R.drawable.ic_star),
                    IconStyle().apply {
                        anchor = PointF(0.5f, 0.5f)
                        flat = true
                        scale = 0.05f
                    }
                )
            }



            // Add venue markers
//            venues.forEach { venue ->
//                val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
//                Log.d(
//                    "Map Point",
//                    "Venue: ${venue.venueName}, Point: ${point.latitude}, ${point.longitude}"
//                )
//
//                val placemark = mapView.map.mapObjects.addPlacemark().apply {
//                    geometry = point
//                    setIcon(
//                        ImageProvider.fromResource(context, R.drawable.baseline_location_on_24_red),
//                        IconStyle().apply {
//                            scale = 2.0f
//                        }
//                    )
//                }
//
//                placemark.userData = venue
//
//                placemark.addTapListener { mapObject, _ ->
//                    val tappedVenue = mapObject.userData as? Venue
//                    tappedVenue?.let {
//                        viewModel.onVenueTapped(it)
//                    }
//                    true
//                }
//            }

            // Add current location marker and move camera
            currentLocation?.let { location ->
                val currentLocationPoint = Point(location.latitude, location.longitude)
                val currentLocationPlacemark =
                    mapView.map.mapObjects.addPlacemark(currentLocationPoint)
                currentLocationPlacemark.setIcon(
                    ImageProvider.fromResource(context, R.drawable.baseline_location_on_24_green),
                    IconStyle().apply {
                        scale = 2.0f // Increase the size of the current location marker
                    }
                )

                Log.d(
                    "Current Location",
                    "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
                )

                // Move camera to the current location
                mapView.map.move(
                    CameraPosition(Point(41.311153, 69.279729), 11.0f, 0.0f, 0.0f)
                )
            } ?: run {
                Log.d("Current Location", "Current location is null")
            }
        }
    )

    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState == LocationPermissionState.DENIED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
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