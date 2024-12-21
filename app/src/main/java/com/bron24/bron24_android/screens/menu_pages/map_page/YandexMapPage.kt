package com.bron24.bron24_android.screens.menu_pages.map_page

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.google.android.play.integrity.internal.t
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class YandexMapPage : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.hello)
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_map))
            return TabOptions(
                index = 2u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {

    }
}

@Composable
fun Banner() {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.kids_playing_soccer_cartoon)
                val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, true) // 100x100 o‘lcham
                val imageProvider = ImageProvider.fromBitmap(resizedBitmap)

                map.move(
                    CameraPosition(
                        Point(41.2995, 69.2401), // Toshkent koordinatalari
                        12.0f, // Zoom darajasi
                        0.0f, // Tilt
                        0.0f  // Azimuth
                    )
                )
                map.mapObjects.addPlacemark(
                    Point(41.2995, 69.2401), // Marker koordinatalari,
                    imageProvider
                )
                map.isZoomGesturesEnabled = true

            }
        },
        update = { mapView ->
            // Xarita parametrlarini o'zgartirish yoki yangilash uchun
        }
    )
}

@Composable
fun YandexMapPageContent() {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapObjects by remember { mutableStateOf<MapObjectCollection?>(null) }
    var userLocationObject by remember { mutableStateOf<MapObject?>(null) }
    var placemarks by remember { mutableStateOf<List<MapObject>>(emptyList()) }

    val defaultLocation = Point(41.2995, 69.2401)


    AndroidView(
        factory = { context ->
            MapView(context).also { view ->
                mapView = view
                mapObjects = view.map.mapObjects
                view.map.move(CameraPosition(defaultLocation, 13f, 0f, 0f))
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            mapObjects?.let { objects ->
                // Remove old placemarks
                placemarks.forEach { placemark ->
                    (placemark.userData as? MapObjectTapListener)?.let { listener ->
                        placemark.removeTapListener(listener)
                    }
                    objects.remove(placemark)
                }
                placemarks = emptyList()

                // Update user location
                defaultLocation.let { location ->
                    val userPoint = Point(location.latitude, location.longitude)
                    userLocationObject?.let { objects.remove(it) }
                    userLocationObject = objects.addPlacemark(userPoint).apply {
                        setIcon(ImageProvider.fromResource(context, R.drawable.ic_star))
                        setIconStyle(IconStyle().setAnchor(PointF(0.5f, 0.5f)))
                    }

                    // Move camera to user location if it's the first time
                    if (view.map.cameraPosition.target == defaultLocation) {
                        view.map.move(
                            CameraPosition(userPoint, 15f, 0f, 0f),
                            Animation(Animation.Type.SMOOTH, 1f),
                            null
                        )
                    }
                }
                val venues = listOf(VenueCoordinates(1, "denov", "40", "33"))

                // Add new venue markers
                placemarks = venues.map { venue ->
                    val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                    val placemark = objects.addPlacemark(point)
                    val isSelected = venue.venueId == 1//id
                    val markerIcon = if (isSelected) {
                        R.drawable.baseline_location_on_24_red
                    } else {
                        R.drawable.baseline_location_on_24_green
                    }
                    val drawable = ContextCompat.getDrawable(context, markerIcon)
                    val bitmap = drawable?.let {
                        getBitmapFromDrawable(it, if (isSelected) 1.8f else 1.5f)
                    }
                    placemark.setIcon(ImageProvider.fromBitmap(bitmap))
                    placemark.userData = venue.venueId
                    val tapListener = MapObjectTapListener { mapObject, point ->
                        if (mapObject.isValid) {
                            //onMarkerClick(venue.venueId)
                            centerCameraOnMarker(view.map, point)
                            true
                        } else {
                            false
                        }
                    }
                    placemark.addTapListener(tapListener)
                    placemark
                }
            }
            view.map.addCameraListener { _, cameraPosition, _, _ ->
                //onCameraPositionChanged(cameraPosition)
            }
        }
    )
    LaunchedEffect(defaultLocation) {
        defaultLocation.let { location ->
            mapView?.map?.move(
                CameraPosition(
                    Point(location.latitude, location.longitude),
                    15f, 0f, 0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YandexMapPagePreview() {
    Banner()
}


