package com.bron24.bron24_android.features.location.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import com.bron24.bron24_android.features.language.presentation.robotoFontFamily

@Composable
fun LocationRequestScreen(
    onAllowClick: () -> Unit,
    onDenyClick: () -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
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
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 80.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 32.sp
                ),
            )

            Text(
                text = stringResource(id = R.string.location_request_title),
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 48.sp
                ),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .height(96.dp)
            )

            Text(
                text = stringResource(id = R.string.location_request_description),
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 20.sp
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(40.dp)
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

            OutlinedButton(
                onClick = {
                    viewModel.setLocationPermissionGranted(false)
                    onDenyClick()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.deny_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = robotoFontFamily
                )
            }
        }
    }
}

@Composable
fun LocationButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFontFamily
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    Bron24_androidTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LocationRequestScreen(
                onAllowClick = { /*TODO*/ },
                onDenyClick = { /*TODO*/ }
            )
        }
    }
}
