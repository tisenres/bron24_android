package com.bron24.bron24_android.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun SimpleYandexMapView() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

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
                map.move(
                    CameraPosition(Point(55.751244, 37.618423), 11.0f, 0.0f, 0.0f)
                )
            }
        }
    )
}

class MapViewLifecycleObserver(
    private val mapView: MapView
) : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        mapView.onStart()
    }

    override fun onStop(owner: LifecycleOwner) {
        mapView.onStop()
    }
}