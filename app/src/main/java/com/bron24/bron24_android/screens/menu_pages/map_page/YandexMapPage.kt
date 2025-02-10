package com.bron24.bron24_android.screens.menu_pages.map_page

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.helper.util.BitmapCache
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePage
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContract
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreenContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

const val TAG = "YandexMapPage"

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

@SuppressLint("UnrememberedMutableState")
@Composable
fun YandexMapPageContent(
    state: State<YandexMapPageContract.UIState>,
    intent: (YandexMapPageContract.Intent) -> Unit,
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = White, darkIcons = true)

    var openDetails by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapObjects by remember { mutableStateOf<MapObjectCollection?>(null) }
    val mapObjectListeners by remember { mutableStateOf<MutableMap<Point, MapObjectTapListener>>(mutableMapOf()) }
    var selectedMarker by remember { mutableStateOf<PlacemarkMapObject?>(null) }
    val venueDetails = state.value.venueDetails
    val locationHasBeenChecked = remember { mutableStateOf(false) }
    val initDataLoaded = state.value.venueCoordinates.isNotEmpty() && state.value.userLocation != null
    val coroutineScope = rememberCoroutineScope()

    var shouldShowBottomSheet by remember {
        mutableStateOf(false)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                intent.invoke(YandexMapPageContract.Intent.RefreshLocation)
            } else {
                ToastManager.showToast(
                    "Локация не разрешена!",
                    ToastType.INFO
                )
            }
            locationHasBeenChecked.value = true
        }
    )

    val userLocation = state.value.userLocation.let {
        Point(it?.latitude ?: 41.311198, it?.longitude ?: 69.279746)
    }
    val tab = LocalTabNavigator.current

    BackHandler {
        tab.current = HomePage
    }
    if(openDetails){
        val states= mutableStateOf(VenueDetailsContract.UIState(state.value.isLoading, venue = state.value.venueDetails, imageUrls = state.value.imageUrls))
        VenueDetailsScreenContent(state = states, back = {
            openDetails = false
            tab.current = HomePage
        }) {
            intent.invoke(YandexMapPageContract.Intent.ClickOrder(it))
        }
    }else{
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
    }
    if (shouldShowBottomSheet) {
        val states= mutableStateOf(VenueDetailsContract.UIState(state.value.isLoadingDetails, venue = state.value.venueDetails, imageUrls = state.value.imageUrls))
        MapVenueDetails(
            state = states,
            modifier = Modifier,
            onOrderPressed = {
                openDetails = true
                shouldShowBottomSheet = false
                intent.invoke(YandexMapPageContract.Intent.ClickVenueBook(it))
            },
            onDismiss = {
                shouldShowBottomSheet = false
                selectedMarker?.let { marker ->
                    intent.invoke(YandexMapPageContract.Intent.DismissVenueDetails)
                    coroutineScope.launch {
                        updateMarkerAppearance(marker, context, false)
                    }
                    selectedMarker = null
                }
            },
        )
    }

    // Ask for location permission
    LaunchedEffect(state.value.checkPermission) {
        if (state.value.checkPermission == LocationPermissionState.DENIED) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Call when all the data is fetched and when mapObjects is initialized
    LaunchedEffect(initDataLoaded) {
        if (initDataLoaded) {
            mapObjects?.let { objects ->
                // Move camera to the location
                userLocation.let { location ->
                    mapView?.map?.move(
                        CameraPosition(
                            Point(location.latitude, location.longitude),
                            15f, 0f, 0f
                        ),
                    )
                }

                // Draw geolocation mark
                setMarkerInStartLocation(objects, userLocation, context)

                // Draw Venue markers
                state.value.venueCoordinates.forEach { venue ->
                    val point = Point(venue.latitude.toDouble(), venue.longitude.toDouble())
                    setVenueMarker(
                        mapView!!,
                        objects,
                        (point to venue),
                        context,
                        intent,
                        mapObjectListeners,
                        onMarkerSelected = { newSelectedMarker ->
                            shouldShowBottomSheet = true
                            selectedMarker?.let { updateMarkerAppearance(it, context, false) }
                            selectedMarker = newSelectedMarker
                            updateMarkerAppearance(newSelectedMarker, context, true)
                        }
                    )
                }
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

fun setVenueMarker(
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
            centerCameraOnVenueMarker(mapView, point.first)
            intent.invoke(YandexMapPageContract.Intent.ClickMarker(point.second))
            onMarkerSelected(placemark)
            true
        }

        mapObjectListeners[point.first] = listener
        placemark.addTapListener(listener)
    }
}

fun updateMarkerAppearance(placemark: PlacemarkMapObject, context: Context, isHighlighted: Boolean) {
    val drawable = if (isHighlighted) R.drawable.red_location_pin else R.drawable.green_location_pin
    val customScale = if (isHighlighted) 1.25f else 0.8f

    val bitmap = BitmapCache.getBitmap(context, drawable)

    placemark.setIcon(
        ImageProvider.fromBitmap(bitmap),
        IconStyle().apply {
            scale = customScale
        }
    )
}

fun centerCameraOnVenueMarker(mapView: MapView, point: Point) {
    val offsetY = 0.004
    val newPoint = Point(point.latitude - offsetY, point.longitude)

    mapView.map.move(
        CameraPosition(newPoint, 15f, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 0.5f),
        null
    )
}

@Preview
@Composable
fun YandexMapPagePreview() {
}


