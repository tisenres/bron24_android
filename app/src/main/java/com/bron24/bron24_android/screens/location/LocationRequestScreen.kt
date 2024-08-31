package com.bron24.bron24_android.screens.location

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun LocationRequestScreen(
    onAllowClick: () -> Unit,
    onDenyClick: () -> Unit,
    viewModel: LocationViewModel = hiltViewModel(),
) {

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.setLocationPermissionGranted(isGranted)
            if (isGranted) {
                onAllowClick()
            } else {
                onDenyClick()
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.checkLocationPermission()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 30.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    color = Color(0xFF32B768),
                    lineHeight = 31.85.sp,
//                    letterSpacing = (-0.78).sp
                ),
            )

            Text(
                text = stringResource(id = R.string.location_request_title),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                    color = Color(0xFF060606),
                    lineHeight = 48.sp
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            Text(
                text = stringResource(id = R.string.location_request_description),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 20.sp
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(40.dp)
                    .width(345.dp)
            )
        }

        Column {
            LocationButton(
                text = stringResource(id = R.string.allow_button),
                onClick = {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            EnhancedOutlinedButton(
                onClick = { viewModel.setLocationPermissionGranted(false) },
                onDenyClick = onDenyClick
            )
        }
    }
}

@Composable
fun EnhancedOutlinedButton(
    onClick: () -> Unit,
    onDenyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "")

    OutlinedButton(
        onClick = {
            onClick()
            onDenyClick()
        },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF26A045)
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFF26A045)),
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .scale(scale)
    ) {
        Text(
            text = stringResource(id = R.string.deny_button),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            lineHeight = 32.sp
        )
    }
}

@Composable
fun LocationButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF26A045)
        ),
        shape = RoundedCornerShape(8.dp),
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .scale(scale)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            lineHeight = 32.sp
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
}