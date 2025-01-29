package com.bron24.bron24_android.screens.menu_pages.map_page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.orbitmvi.orbit.compose.collectAsState
import kotlin.math.abs
import kotlin.math.roundToInt

private const val TAG = "YandexMapPage"

object YandexMapPage : Tab {
    private fun readResolve(): Any = YandexMapPage
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.nearby)
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
        val viewModel: YandexMapPageContract.ViewModel = getViewModel<YandexMapPageVM>()
        remember {
            viewModel.initData()
        }
        val uiState = viewModel.collectAsState()
        YandexMapPageContent(uiState, viewModel::onDispatchers)
    }
}

@Composable
fun YandexMapPageContent(
    state: State<YandexMapPageContract.UIState>,
    intent: (YandexMapPageContract.Intent) -> Unit
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapObjects by remember { mutableStateOf<MapObjectCollection?>(null) }
    val mapObjectListeners by remember { mutableStateOf<MutableMap<Point, MapObjectTapListener>>(mutableMapOf()) }
    val firstUpdateTime = remember { mutableLongStateOf(0L) }
    var venueCardIsVisible by remember { mutableStateOf(false) }

    val userLocation = state.value.userLocation.let {
        Point(it.latitude, it.longitude)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                Log.d(TAG, "Factory was called")
                MapView(context).also { view ->
                    mapView = view
                    mapObjects = view.map.mapObjects

                    view.map.isZoomGesturesEnabled = true
                    view.map.isRotateGesturesEnabled = true
                    view.map.isTiltGesturesEnabled = true
                    view.map.isScrollGesturesEnabled = true

                    view.map.move(
                        CameraPosition(userLocation, 15f, 0f, 0f)
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                // No-op to prevent unnecessary updates
            }
        )

        LaunchedEffect(mapObjects) {
            mapObjects?.let { objects ->
                setMarkerInStartLocation(objects, userLocation, context)
                state.value.venueCoordinates.forEach { venue ->
                    val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                    setStadiumMarker(
                        mapView!!,
                        objects,
                        (point to venue),
                        context,
                        intent,
                        mapObjectListeners
                    )
                }

//                mapView?.map?.invalidate()
            }
        }

        ZoomControls(
            modifier = Modifier.align(Alignment.TopEnd),
            mapView = mapView,
            userLocation = userLocation
        )

        AnimatedVisibility(
            visible = state.value.venueDetails != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            SwipeToDismissVertical(
                content = {
                    val venueDetails = state.value.venueDetails
                    if (venueDetails != null) {
                        MapVenueDetails(
                            venueDetails = venueDetails,
                            modifier = Modifier,
                            onOrderPressed = {
                                intent.invoke(YandexMapPageContract.Intent.ClickVenueBook(venueDetails = venueDetails))
                            },
                            imageUrls = state.value.imageUrls
                        )
                    } else {
                        CircularProgressIndicator()
                    }
                },
                onDismiss = {
                    venueCardIsVisible = false
                }
            )
        }

        LaunchedEffect(userLocation) {
            if (firstUpdateTime.value == 0L) {
                firstUpdateTime.value = System.currentTimeMillis()
            }
            if (System.currentTimeMillis() - firstUpdateTime.value <= 3000L) {
                userLocation.let { location ->
                    mapView?.map?.move(
                        CameraPosition(
                            Point(location.latitude, location.longitude),
                            15f, 0f, 0f
                        ),
                    )
                }
            }
        }

        DisposableEffect(Unit) {
            mapView?.onStart()
            onDispose {
                mapView?.onStop()
                mapView?.map?.mapObjects?.clear()
                mapView = null
            }
        }
    }
}

@Composable
fun SwipeToDismissVertical(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    val maxOffset = 500f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    offsetY += dragAmount
                    if (abs(offsetY) > maxOffset) {
                        onDismiss()
                    }
                }
            }
            .offset { IntOffset(0, offsetY.roundToInt()) }
    ) {
        content()
    }
}

private fun setMarkerInStartLocation(
    mapObjects: MapObjectCollection,
    location: Point,
    context: Context
) {
    if (mapObjects == null) {
        Log.e(TAG, "mapObjects is null")
        return
    }

    val marker = createBitmapFromVector(R.drawable.location_pin_svg, context)
    if (marker == null) {
        Log.e(TAG, "Failed to create bitmap from vector")
        return
    }

    mapObjects.addPlacemark(
        location,
        ImageProvider.fromBitmap(marker)
    )
    Log.d(TAG, "Marker added at location: $location")
}

fun setStadiumMarker(
    mapView: MapView,
    mapObjects: MapObjectCollection,
    point: Pair<Point, VenueCoordinates>,
    context: Context,
    intent: (YandexMapPageContract.Intent) -> Unit,
    mapObjectListeners: MutableMap<Point, MapObjectTapListener> = mutableMapOf(),
) {
    val marker = createBitmapFromVector(R.drawable.green_location_pin, context)

    val placemark = mapObjects.addPlacemark(
        point.first,
        ImageProvider.fromBitmap(marker)
    )

    if (!mapObjectListeners.containsKey(point.first)) {
        val listener = MapObjectTapListener { _, _ ->
            centerCameraOnMarker(mapView, point.first)
            updateMarkerAppearance(placemark, context, isHighlighted = true)
            intent.invoke(YandexMapPageContract.Intent.ClickMarker(point.second))
            true
        }

        mapObjectListeners[point.first] = listener
        placemark.addTapListener(listener)
    }
}

fun updateMarkerAppearance(placemark: PlacemarkMapObject, context: Context, isHighlighted: Boolean) {
    val drawable = if (isHighlighted) R.drawable.red_location_pin else R.drawable.green_location_pin
    val customScale = if (isHighlighted) 1.25f else 0.8f

    val bitmap = createBitmapFromVector(drawable, context)

    placemark.setIcon(
        ImageProvider.fromBitmap(bitmap),
        IconStyle().apply {
            scale = customScale
        }
    )
}

fun centerCameraOnMarker(mapView: MapView, point: Point) {
    val offsetY = 0.004
    val newPoint = Point(point.latitude - offsetY, point.longitude)

    mapView.map.move(
        CameraPosition(newPoint, 15f, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 0.5f),
        null
    )
}

private fun createBitmapFromVector(art: Int, context: Context): Bitmap? {
    val drawable = ContextCompat.getDrawable(context, art) ?: return null
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

@Preview
@Composable
fun YandexMapPagePreview() {
}


