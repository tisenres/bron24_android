package com.bron24.bron24_android.features.map.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bron24.bron24_android.core.domain.entities.Venue
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView

@Composable
fun VenueMapView(
    viewModel: VenueMapViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.observeAsState(emptyList())

    MapView(
        venues = venues
    )
}

@Composable
fun MapView(
    venues: List<Venue>
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        MapKitFactory.initialize(context)
        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                // Perform initial map setup here
                map.move(
                    CameraPosition(Point(55.751244, 37.618423), 11.0f, 0.0f, 0.0f)
                )
                venues.forEach { venue ->
                    val point =
                        Point(venue.address.latitude.toDouble(), venue.address.longitude.toDouble())
                    val placemark = map.mapObjects.addPlacemark(point)
                    placemark.userData = venue
                    placemark.addTapListener(MapObjectTapListener { mapObject, point ->
                        val tappedVenue = mapObject.userData as? Venue
                        // Handle tap event
                        true
                    })
                }
            }
//            }.also { ManageMapLifecycle(it) }
        },
        update = { mapView ->
            // Update map when venues change
            mapView.map.mapObjects.clear()
            venues.forEach { venue ->
                val point = Point(venue.address.latitude.toDouble(), venue.address.longitude.toDouble())
                val placemark = mapView.map.mapObjects.addPlacemark(point)
                placemark.userData = venue
                placemark.addTapListener(MapObjectTapListener { mapObject, point ->
                    val tappedVenue = mapObject.userData as? Venue
                    // Handle tap event
                    true
                })
            }
        }
    )
}

@Composable
fun ManageMapLifecycle(mapView: MapView) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
//                Lifecycle.Event.ON_RESUME -> mapView.onResume()
//                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
//                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}