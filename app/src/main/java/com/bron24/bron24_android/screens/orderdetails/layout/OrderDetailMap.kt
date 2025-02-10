package com.bron24.bron24_android.screens.orderdetails.layout

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.orderdetails.OrderDetailsContact
import com.valentinilk.shimmer.shimmer
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun OrderDetailMap(state: State<OrderDetailsContact.UIState>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    DisposableEffect(lifecycleOwner) {
        val mapKit = MapKitFactory.getInstance()
        mapKit.onStart()
        mapView = MapView(context).apply { onStart() }

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    mapKit.onStart()
                    mapView?.onStart()
                }

                Lifecycle.Event.ON_STOP -> {
                    mapView?.onStop()
                    mapKit.onStop()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView?.onStop()
            mapKit.onStop()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                //openMapWithOptions(context, order.lat, order.lon, order.venueName)
            }
            .background(Color(0xffFAFAFA))
    ) {
        if(state.value.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
        }else{
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                ) {
                    mapView?.let { map ->
                        AndroidView(
                            factory = { map },
                            modifier = Modifier.fillMaxSize(),
                            update = { view ->
                                state.value.order?.let {order->
                                    try {
                                        val venueLocation = Point(order.latitude, order.longitude)
                                        view.map.move(
                                            CameraPosition(venueLocation, 15.0f, 0.0f, 0.0f),
                                            Animation(Animation.Type.SMOOTH, 0.4f),
                                            null
                                        )
                                        view.map.mapObjects.clear()

                                        val placemark = view.map.mapObjects.addPlacemark(venueLocation)
                                        val markerIcon = R.drawable.baseline_location_on_24_red
                                        val drawable = ContextCompat.getDrawable(context, markerIcon)
                                        val bitmap = drawable?.let {
                                            getBitmapFromDrawable(it,1.5f)
                                        }
                                        placemark.setIcon(ImageProvider.fromBitmap(bitmap))

                                        // Disable user interaction with the map
                                        view.map.isScrollGesturesEnabled = false
                                        view.map.isZoomGesturesEnabled = false
                                        view.map.isTiltGesturesEnabled = false
                                        view.map.isRotateGesturesEnabled = false

                                        errorMessage = null
                                    } catch (e: Exception) {
                                        errorMessage = "Error loading map: ${e.message}"
                                    }
                                }

                            }
                        )
                    }
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                MapDetails(state)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = Color(0xFFD4D4D4)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 12.dp)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.open_map),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            color = Color(0xFF3C2E56),
                            lineHeight = 14.7.sp
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(id = R.string.open_map),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
        //Clickable overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    state.value.order?.let { venue ->
                        openMapWithOptions(
                            context,
                            latitude = venue.latitude,
                            longitude = venue.longitude,
                            venue.venueName
                        )
                    }
                }
        )
    }
}

private fun openMapWithOptions(
    context: Context,
    latitude: Double,
    longitude: Double,
    venueName: String
) {
    val encodedName = Uri.encode(venueName)
    val uriString = "geo:$latitude,$longitude?q=$latitude,$longitude($encodedName)"
    val geoUri = Uri.parse(uriString)
    val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)

    val packageManager = context.packageManager
    val activities = packageManager.queryIntentActivities(mapIntent, 0)

    if (activities.isNotEmpty()) {
        val chooserIntent = Intent.createChooser(mapIntent, "Open map with")
        context.startActivity(chooserIntent)
    } else {
        // If no map apps are installed, open in browser
        val browserUri =
            Uri.parse("https://www.openstreetmap.org/?mlat=$latitude&mlon=$longitude#map=15/$latitude/$longitude")
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}

private fun getBitmapFromDrawable(drawable: Drawable, scaleFactor: Float = 1.5f): Bitmap {
    val width = (drawable.intrinsicWidth * scaleFactor).toInt()
    val height = (drawable.intrinsicHeight * scaleFactor).toInt()
    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
        val canvas = android.graphics.Canvas(this)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
    }
}


@Composable
private fun MapDetails(state: State<OrderDetailsContact.UIState>) {
    if (state.value.isLoading) {
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.2f))
        )
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.2f))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = "${state.value.order?.address?.district} ${state.value.order?.address?.addressName}",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 17.5.sp
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        if(state.value.order?.distance?.toInt()!=0){
            DistanceInfo(
                icon = R.drawable.mingcute_navigation_fill,
                text = "${String.format("%.2f",state.value.order?.distance?:10)} ${stringResource(id = R.string.km)} ${stringResource(id = R.string.from_you)}",
                tintColor = Color(0xFFB7B3B3),
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        DistanceInfo(
            icon = R.drawable.ic_metro,
            text = state.value.order?.address?.closestMetroStation?:"",
            tintColor = Color(0xFFD43535),
        )
    }
}

@Composable
private fun DistanceInfo(icon: Int, text: String, tintColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tintColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFFB7B3B3),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

