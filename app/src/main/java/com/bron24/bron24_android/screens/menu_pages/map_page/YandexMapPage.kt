package com.bron24.bron24_android.screens.menu_pages.map_page

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.compose.collectAsState

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
    var selectedMarker by remember { mutableStateOf<PlacemarkMapObject?>(null) }
    val venueDetails = state.value.venueDetails

    val shouldShowBottomSheet = remember(state.value.isLoading, state.value.venueDetails) {
        !state.value.isLoading && state.value.venueDetails != null
    }

    val coroutineScope = rememberCoroutineScope()

    val userLocation = state.value.userLocation.let {
        Point(it.latitude, it.longitude)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
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
            modifier = Modifier.fillMaxSize()
        )

        ZoomControls(
            modifier = Modifier.align(Alignment.TopEnd),
            mapView = mapView,
            userLocation = userLocation
        )
    }

    if (shouldShowBottomSheet) {
        MapVenueDetails(
            venueDetails = venueDetails!!,
            modifier = Modifier,
            onOrderPressed = {
                intent.invoke(YandexMapPageContract.Intent.ClickVenueBook(venueDetails = venueDetails))
            },
            onDismiss = {
                selectedMarker?.let { marker ->
                    intent.invoke(YandexMapPageContract.Intent.DismissVenueDetails)
                    coroutineScope.launch {
                        updateMarkerAppearance(marker, context, false)
                    }
                    selectedMarker = null
                }
            },
            imageUrls = state.value.imageUrls
        )
    }

    LaunchedEffect(state.value.isLoading) {
        Log.d(TAG, "Loading: ${state.value.isLoading}")
    }

    LaunchedEffect(userLocation) {
        if (firstUpdateTime.value == 0L) {
            firstUpdateTime.value = System.currentTimeMillis()
        }
        if (System.currentTimeMillis() - firstUpdateTime.longValue <= 3000L) {
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

    LaunchedEffect(mapObjects, state.value.venueCoordinates) {
        mapObjects?.let { objects ->
            // Always set the start location marker.
            setMarkerInStartLocation(objects, userLocation, context)

            // Add markers for each venue.
            state.value.venueCoordinates.forEach { venue ->
                val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                setStadiumMarker(
                    mapView!!,
                    objects,
                    (point to venue),
                    context,
                    intent,
                    mapObjectListeners,
                    onMarkerSelected = { newSelectedMarker ->
                        coroutineScope.launch(Dispatchers.Main) {
                            selectedMarker?.let { updateMarkerAppearance(it, context, false) }
                            selectedMarker = newSelectedMarker
                            updateMarkerAppearance(newSelectedMarker, context, true)
                        }
                    }
                )
            }
        }
    }

    LaunchedEffect(selectedMarker) {
        selectedMarker?.let {
            updateMarkerAppearance(it, context, true)
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

private fun setMarkerInStartLocation(
    mapObjects: MapObjectCollection,
    location: Point,
    context: Context
) {
    val bitmap = BitmapCache.getBitmap(context, R.drawable.location_pin_svg)

    mapObjects.addPlacemark(
        location,
        ImageProvider.fromBitmap(bitmap)
    )
}

suspend fun setStadiumMarker(
    mapView: MapView,
    mapObjects: MapObjectCollection,
    point: Pair<Point, VenueCoordinates>,
    context: Context,
    intent: (YandexMapPageContract.Intent) -> Unit,
    mapObjectListeners: MutableMap<Point, MapObjectTapListener> = mutableMapOf(),
    onMarkerSelected: (PlacemarkMapObject) -> Unit
) {
    val bitmap = BitmapCache.getBitmap(context, R.drawable.green_location_pin)

    val placemark = mapObjects.addPlacemark(
        point.first,
        ImageProvider.fromBitmap(bitmap)
    )

    updateMarkerAppearance(
        placemark,
        context,
        isHighlighted = false
    )

    if (!mapObjectListeners.containsKey(point.first)) {
        val listener = MapObjectTapListener { _, _ ->
            centerCameraOnMarker(mapView, point.first)
            intent.invoke(YandexMapPageContract.Intent.ClickMarker(point.second))
            onMarkerSelected(placemark)
            true
        }

        mapObjectListeners[point.first] = listener
        placemark.addTapListener(listener)
    }
}

suspend fun updateMarkerAppearance(placemark: PlacemarkMapObject, context: Context, isHighlighted: Boolean) {
    val drawable = if (isHighlighted) R.drawable.red_location_pin else R.drawable.green_location_pin
    val customScale = if (isHighlighted) 1.25f else 0.8f

    val bitmap = BitmapCache.getBitmap(context, drawable)

    withContext(Dispatchers.Main) {
        placemark.setIcon(
            ImageProvider.fromBitmap(bitmap),
            IconStyle().apply {
                scale = customScale
            }
        )
    }
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

//private suspend fun createBitmapFromVector(art: Int, context: Context): Bitmap? = withContext(Dispatchers.IO) {
//    Log.d(TAG, "Creating bitmap from vector resource: $art")
//    val drawable = ContextCompat.getDrawable(context, art) ?: return@withContext null
//    val bitmap = Bitmap.createBitmap(
//        drawable.intrinsicWidth,
//        drawable.intrinsicHeight,
//        Bitmap.Config.ARGB_8888
//    )
//    val canvas = Canvas(bitmap)
//    drawable.setBounds(0, 0, canvas.width, canvas.height)
//    drawable.draw(canvas)
//    bitmap
//}

@Preview
@Composable
fun YandexMapPagePreview() {
}


