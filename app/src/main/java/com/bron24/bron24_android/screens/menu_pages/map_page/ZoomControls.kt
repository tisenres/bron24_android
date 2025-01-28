package com.bron24.bron24_android.screens.menu_pages.map_page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bron24.bron24_android.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

private const val MAX_ZOOM_LEVEL = 20f
private const val MIN_ZOOM_LEVEL = 5f

@Composable
fun ZoomControls(modifier: Modifier, mapView: MapView?, userLocation: Point) {
    Column(
        modifier = modifier
            .padding(top = 28.dp, end = 14.dp),
        horizontalAlignment = Alignment.End
    ) {

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .size(60.dp)
                .clickable {
                    val currentZoom = mapView?.map?.cameraPosition?.zoom ?: 13f
                    if (currentZoom < MAX_ZOOM_LEVEL) {
                        mapView?.map?.move(
                            CameraPosition(
                                mapView.map?.cameraPosition?.target ?: userLocation,
                                currentZoom + 1f,
                                0f,
                                0f
                            ),
                            Animation(Animation.Type.SMOOTH, 0.5f),
                            null
                        )
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Zoom Out",
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .size(60.dp)
                .clickable {
                    mapView?.map?.move(
                        CameraPosition(
                            mapView.map?.cameraPosition?.target ?: userLocation,
                            (mapView.map?.cameraPosition?.zoom ?: 13f) - 1f,
                            0f,
                            0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0.5f),
                        null
                    )
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Zoom Out",
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(120.dp))

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .size(60.dp)
                .clickable {
                    mapView?.map?.move(
                        CameraPosition(
                            userLocation,
                            14f,
                            0f,
                            0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0.5f),
                        null
                    )
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.near_me),
                contentDescription = "Zoom Out",
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            )
        }
    }
}