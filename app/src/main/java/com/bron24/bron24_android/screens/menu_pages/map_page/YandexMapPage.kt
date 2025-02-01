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
//    val venueDetails by remember { derivedStateOf { state.value.venueDetails } }
    var selectedMarker by remember { mutableStateOf<PlacemarkMapObject?>(null) }
    val imageUrls = remember(state.value.imageUrls) { state.value.imageUrls }

    val venueDetails = state.value.venueDetails
    var showBottomSheet by remember { mutableStateOf(venueDetails != null) }

    val coroutineScope = rememberCoroutineScope()

    val userLocation = state.value.userLocation.let {
        Point(it.latitude, it.longitude)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                Log.d(TAG, "Factory was called: Creating MapView")
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
                    Log.d(TAG, "MapView initialized and camera moved to user location: $userLocation")
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

    if (showBottomSheet && venueDetails != null) {
        MapVenueDetails(
            venueDetails = venueDetails,
            modifier = Modifier,
            onOrderPressed = {
                Log.d(TAG, "Order pressed for venue: ${venueDetails.venueName}")
                intent.invoke(YandexMapPageContract.Intent.ClickVenueBook(venueDetails = venueDetails))
            },
            onDismiss = {
                Log.d(TAG, "BottomSheet dismissed")
                // Reset marker appearance as before
                selectedMarker?.let { marker ->
                    coroutineScope.launch {
                        updateMarkerAppearance(marker, context, false)
                    }
                }
                showBottomSheet = false
                intent.invoke(YandexMapPageContract.Intent.DismissVenueDetails)
            },
            imageUrls = imageUrls
        )
    }

    LaunchedEffect(userLocation) {
        if (firstUpdateTime.value == 0L) {
            firstUpdateTime.value = System.currentTimeMillis()
        }
        if (System.currentTimeMillis() - firstUpdateTime.value <= 3000L) {
            Log.d(TAG, "Moving camera to user location: $userLocation")
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

    LaunchedEffect(venueDetails) {
        showBottomSheet = venueDetails != null
    }

    LaunchedEffect(mapObjects) {
        mapObjects?.let { objects ->
            Log.d(TAG, "MapObjects initialized: Adding markers")
            setMarkerInStartLocation(objects, userLocation, context)
            state.value.venueCoordinates.forEach { venue ->
                val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                Log.d(TAG, "Adding marker for venue: ${venue.venueName} at $point")
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
        Log.d(TAG, "MapView started")
        mapView?.onStart()
        onDispose {
            Log.d(TAG, "MapView stopped and resources cleared")
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
    Log.d(TAG, "User Location Marker added at location: $location")
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
            Log.d(TAG, "Marker tapped: ${point.second.venueName}")
            onMarkerSelected(placemark)
            centerCameraOnMarker(mapView, point.first)
            intent.invoke(YandexMapPageContract.Intent.ClickMarker(point.second))
            true
        }

        mapObjectListeners[point.first] = listener
        placemark.addTapListener(listener)
    }
    Log.d(TAG, "Marker added at location: ${point.first}")
}

suspend fun updateMarkerAppearance(placemark: PlacemarkMapObject, context: Context, isHighlighted: Boolean) {
    Log.d(TAG, "Updating marker appearance: isHighlighted = $isHighlighted")
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
    Log.d(TAG, "Centering camera on marker at: $point")
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


