package com.bron24.bron24_android.screens.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun BookingSuccessScreen(
//    viewModel: BookingViewModel = hiltViewModel(),
    onMyOrdersClick: () -> Unit,
    onMainPageClick: () -> Unit,
    onMapClick: () -> Unit
) {
//    val bookingInfo by viewModel.bookingInfo.collectAsState()
    val bookingInfo = BookingSuccessInfo("63 65 82", "Bunyodkor kompleksi", "21.02.2024 9:00")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 19.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.success_booking),
                contentDescription = "Booking Success",
                modifier = Modifier
                    .size(290.dp)
            )

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Success !",
                color = Color(0xFF3C2E56),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 30.sp,
                ),
            )

            Spacer(modifier = Modifier.height(27.dp))

            BookingInfoCard(bookingInfo, onMapClick)

            Spacer(modifier = Modifier.weight(1f))

            ConfirmButton(
                isEnabled = true,
                title = "My orders",
                onClick = { /* Handle confirm button click */ },
            )
            Spacer(modifier = Modifier.height(3.dp))

            MainPageButton(
                isEnabled = true,
                title = "Main page",
                onClick = { /* Handle confirm button click */ }
            )
        }
    }
}

@Composable
fun BookingInfoCard(bookingInfo: BookingSuccessInfo, onMapClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFA4ECC3))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 56.dp)  // Add padding to prevent text overlap with the button
            ) {
                Text(
                    text = "Your online queue number",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = bookingInfo.queueNumber,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = bookingInfo.venueName,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = bookingInfo.dateTime,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable {
                        onMapClick
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_map_cute),
                    contentDescription = "map_icon",
                    colorFilter = ColorFilter.tint(Color(0xFF3DDA7E)),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun MainPageButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    TextButton(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color(0xFF32B768)
        ),
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 32.sp,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookingScreenPreview() {
    BookingSuccessScreen({}, {}, {})
}