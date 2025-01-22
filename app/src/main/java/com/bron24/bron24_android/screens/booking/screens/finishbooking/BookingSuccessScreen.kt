package com.bron24.bron24_android.screens.booking.screens.finishbooking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.ConfirmButton
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BookingSuccessScreen(private val info: VenueOrderInfo) : Screen {
    @Composable
    override fun Content() {
        val viewModel: BookingSuccessContract.ViewModel = getViewModel<BookingSuccessVM>()
        BookingSuccessContent(
            info = rememberUpdatedState(newValue = info),
            intent = viewModel::onDispatchers
        )
    }
}

@Composable
fun BookingSuccessContent(
    info: State<VenueOrderInfo>,
    intent: (BookingSuccessContract.Intent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 19.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            item { Spacer(modifier = Modifier.weight(1f)) }

            item {
                Image(
                    painter = painterResource(id = R.drawable.success_booking),
                    contentDescription = "Booking Success",
                    modifier = Modifier
                        .size(290.dp)
                )
            }

//            Spacer(modifier = Modifier.height(45.dp))

            item {
                Text(
                    text = "Success!",
                    color = Color(0xFF3C2E56),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = Color(0xFF3C2E56),
                        lineHeight = 40.sp,
                    ),
                )
            }

//            Spacer(modifier = Modifier.height(27.dp))

            item {
                BookingInfoCard(info.value) {

                }
            }

//            Spacer(modifier = Modifier.weight(1f))

            item {
                ConfirmButton(
                    isEnabled = true,
                    title = "My orders",
                    onClick = {
                        intent.invoke(BookingSuccessContract.Intent.ClickOrder)
                    }
                )
            }
//            Spacer(modifier = Modifier.height(3.dp))

            item {
                MainPageButton(
                    isEnabled = true,
                    title = "Main page",
                    onClick = {
                        intent.invoke(BookingSuccessContract.Intent.ClickMenu)
                    }
                )
            }
        }
    }
}

@Composable
fun BookingInfoCard(info: VenueOrderInfo, onMapClick: () -> Unit) {
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
                    .padding(end = 56.dp)
            ) {
                Text(
                    text = "Order details",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Text(
                    text = info.venueName,
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = formatDate(info.date),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    items(info.orderId.size) { index ->
                        Text(
                            text = "Order ID: " + info.orderId[index],
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color.Black,
                                lineHeight = 20.sp,
                            ),
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "Time: " + info.resTimeSlot[index],
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = Color.Black,
                                lineHeight = 20.sp,
                            ),
                        )
                    }
                }

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

fun formatDate(inputDate: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // assuming input format is "yyyy-MM-dd"
    val date: Date = inputFormatter.parse(inputDate) ?: return ""  // parse the string into Date
    val outputFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) // desired output format
    return outputFormatter.format(date)
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