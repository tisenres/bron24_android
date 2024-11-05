//package com.bron24.bron24_android.screens.cityselection.presentation
//
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.bron24.bron24_android.R
//import com.bron24.bron24_android.screens.main.theme.Bron24_androidTheme
//import com.bron24.bron24_android.screens.cityselection.domain.entities.City
//import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
//
//@Composable
//fun CitySelectionScreen(
//    viewModel: CityViewModel,
//    onNavigateToLocationRequest: () -> Unit
//) {
//    val selectedCity by viewModel.selectedCity.collectAsState()
//    val availableCities by viewModel.availableCities.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.primary)
//            .padding(top = 80.dp, end = 24.dp, bottom = 24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(horizontalAlignment = Alignment.Start) {
//            Text(
//                text = stringResource(id = R.string.app_name),
//                modifier = Modifier
//                    .padding(start = 24.dp),
//                style = TextStyle(
//                    fontFamily = gilroyFontFamily,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    color = MaterialTheme.colorScheme.secondary,
//                    lineHeight = 32.sp
//                ),
//            )
//
//            Text(
//                text = stringResource(id = R.string.select_city),
//                modifier = Modifier
//                    .padding(top = 24.dp, start = 24.dp, bottom = 20.dp)
//                    .height(128.dp),
//                style = TextStyle(
//                    fontFamily = gilroyFontFamily,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 48.sp,
//                    color = MaterialTheme.colorScheme.secondary,
//                    lineHeight = 64.sp
//                ),
//            )
//
//            LazyColumn {
//                items(availableCities) { city ->
//                    CityOption(
//                        city = city,
//                        isSelected = selectedCity == city,
//                        onClick = { viewModel.selectCity(city) },
//                        modifier = Modifier
//                            .padding(top = 16.dp)
//                    )
//                }
//            }
//        }
//
//        ConfirmButton(
//            isEnabled = selectedCity != null,
//            onClick = {
//                viewModel.confirmCitySelection()
//                onNavigateToLocationRequest()
//            }
//        )
//    }
//}
//
//@Composable
//fun CityOption(
//    city: City,
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var isPressed by remember { mutableStateOf(false) }
//
//    val animatedColor by animateColorAsState(
//        targetValue = if (isPressed) MaterialTheme.colorScheme.primary else if (isSelected) Color(0xFF26A045) else Color(0xFFE4E4E4),
//        label = ""
//    )
//
//    Row(
//        verticalAlignment = Alignment.Top,
//        modifier = modifier
//            .fillMaxWidth()
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = {
//                        isPressed = true
//                        tryAwaitRelease()
//                        isPressed = false
//                        onClick()
//                    }
//                )
//            }
//    ) {
//        Box(
//            modifier = Modifier
//                .width(4.dp)
//                .height(48.dp)
//                .background(
//                    if (isSelected) Color(0xFF26A045) else Color.Transparent,
//                    shape = RoundedCornerShape(3.dp)
//                )
//        )
//        Spacer(modifier = Modifier.width(20.dp))
//        Text(
//            text = city.displayName,
//            modifier = Modifier
//                .height(64.dp),
//            style = TextStyle(
//                color = animatedColor,
//                fontSize = 48.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = gilroyFontFamily,
//                lineHeight = 64.sp
//            ),
//        )
//    }
//}
//
//@Composable
//fun ConfirmButton(
//    isEnabled: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(
//            contentColor = Color.White,
//            containerColor = if (isEnabled) Color(0xFF26A045) else Color(0xFFE4E4E4)
//        ),
//        enabled = isEnabled,
//        shape = RoundedCornerShape(8.dp),
//        modifier = modifier
//            .fillMaxWidth()
//            .height(52.dp)
//            .padding(start = 24.dp)
//    ) {
//        Text(
//            text = stringResource(id = R.string.next),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Normal,
//            fontFamily = gilroyFontFamily,
//            color = if (isEnabled) Color.White else Color.Gray,
//            lineHeight = 32.sp
//        )
//    }
//}
//
//@Preview
//@Composable
//fun SimpleComposablePreview() {
//    Bron24_androidTheme {
//        // Add preview content here if needed
//    }
//}
