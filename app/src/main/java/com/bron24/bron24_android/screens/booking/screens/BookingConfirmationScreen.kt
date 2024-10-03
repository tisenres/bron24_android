package com.bron24.bron24_android.screens.booking.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun BookingConfirmationScreen(
    viewModel: BookingConfirmationViewModel,
    venueId: Int,
    date: String,
    sector: String,
    timeSlots: List<TimeSlot>
) {
    var showPaymentMethods by remember { mutableStateOf(false) }
    var showPromoCode by remember { mutableStateOf(false) }

    val booking by viewModel.booking.collectAsState()
    val isLoading by remember { viewModel.isLoading }

    // Fetch booking information when screen is launched
    LaunchedEffect(Unit) {
        viewModel.getBookingInfo(venueId, date, sector, timeSlots)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 10.dp)
            ) {
                TopAppBar()
                booking?.let {
                    BookingInfoCard(it)
                    Spacer(modifier = Modifier.height(15.dp))
                    PaymentMethodButton { showPaymentMethods = true }
                    Spacer(modifier = Modifier.height(15.dp))
                    PromoCodeButton { showPromoCode = true }
                    Spacer(modifier = Modifier.height(15.dp))
                    TotalAmount(it.cost ?: 0)
                    Spacer(modifier = Modifier.weight(1f))
                    ConfirmButton(
                        isEnabled = true,
                        onClick = { /* Handle confirm button click */ },
                        title = stringResource(id = R.string.confirm)
                    )
                } ?: run {
                    // Handle case where booking is null (e.g., error or no data)
                    Text("Failed to load booking information", color = Color.Red)
                }
            }
        }

        if (showPaymentMethods) {
            PaymentMethodsBottomSheet { showPaymentMethods = false }
        }

        if (showPromoCode) {
            PromoCodeBottomSheet(
                onDismiss = { showPromoCode = false },
                onApply = { code ->
                    // Handle promo code application
                    // viewModel.applyPromoCode(code)
                    showPromoCode = false
                }
            )
        }
    }
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = "Booking information",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )
        )
    }
}

@Composable
fun BookingInfoCard(booking: Booking) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
    ) {
        Column(modifier = Modifier.padding(vertical = 15.dp)) {
            VenueInfo(
                booking.venueName ?: "", booking.venueAddress ?: "",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Divider(modifier = Modifier.padding(vertical = 15.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                BookingDetail("DATE", booking.bookingDate)
                Spacer(modifier = Modifier.height(15.dp))

                // Add TimeSlots Column
                Text(
                    text = "TIME",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color(0xFF949494),
                        lineHeight = 20.sp
                    )
                )
                Column {
                    booking.timeSlots.forEach { timeSlot ->
                        TimeSlotItem(timeSlot)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
                BookingDetail(
                    "STADIUM PART",
                    when (booking.sector) {
                        "X" -> "Full Stadium"
                        else -> "Sector ${booking.sector}"
                    }
                )
            }
            Divider(modifier = Modifier.padding(vertical = 15.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                BookingDetail("FULL NAME", booking.firstName + " " + booking.lastName)
                Spacer(modifier = Modifier.height(15.dp))
                BookingDetail("FIRST NUMBER", booking.phoneNumber)
                Spacer(modifier = Modifier.height(15.dp))
                SecondNumberField()
            }
        }
    }
}

@Composable
fun SecondNumberField() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "SECOND NUMBER",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 20.sp,
            )
        )
        Text(
            text = "+998927372376",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )
        )
    }
}

@Composable
fun TimeSlotItem(timeSlot: TimeSlot) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = timeSlot.startTime + "-" + timeSlot.endTime,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
        )
    }
}

@Composable
fun VenueInfo(venue: String, address: String, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.venue_icon),
            contentDescription = "Venue",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = venue,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = address,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = Color(0xFF949494),
                    lineHeight = 16.sp,
                )
            )
        }
    }
}

@Composable
fun BookingDetail(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 20.sp,
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )
        )
    }
}

@Composable
fun PaymentMethodButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(start = 15.dp, end = 20.dp, top = 21.dp, bottom = 21.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_payment_24),
                        contentDescription = "Cash",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(17.dp))
                Text(
                    "Choose Payment",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    )
                )
            }
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Choose",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun PromoCodeBottomSheet(
    onDismiss: () -> Unit,
    onApply: (String) -> Unit
) {
    var promoCode by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
            ) {
                Text(
                    "Enter promo code",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        lineHeight = 22.sp,
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = promoCode,
                    onValueChange = { promoCode = it },
                    label = { Text("Promo code") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        onApply(promoCode)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF32B768))
                ) {
                    Text(
                        "Apply",
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        )
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = Color.Black.copy(alpha = 0.5f),
        content = {}
    )

    LaunchedEffect(sheetState) {
        if (sheetState.currentValue != ModalBottomSheetValue.Expanded) {
            onDismiss()
        }
    }
}

@Composable
fun PromoCodeButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(start = 15.dp, end = 20.dp, top = 21.dp, bottom = 21.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.promo_code),
                        contentDescription = "Promo Code",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(17.dp))
                Text(
                    "Enter promo code",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    )
                )
            }
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Choose",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun TotalAmount(total: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "TOTAL",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF949494),
                lineHeight = 20.sp,
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            total.toString(),
            fontSize = 18.sp,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
        )
    }
}

@Composable
fun ConfirmButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (isEnabled) Color(0xFF32B768) else Color(0xFFE4E4E4),
        contentColor = Color.White,
        disabledContainerColor = Color(0xFFE4E4E4),
        disabledContentColor = Color.Gray
    )

    Button(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        colors = colors,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp
        )
    }
}

@Composable
fun PaymentMethodsBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)) {
                Text(
                    text = "Payment methods",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))
                PaymentOption("Cash", R.drawable.cash_pic, isSelected = true)
                Spacer(modifier = Modifier.height(10.dp))
//                PaymentOption(
//                    "UzCard **** **** **** 0961",
//                    R.drawable.uzcard,
//                    isSelected = false
//                )
                Spacer(modifier = Modifier.height(10.dp))
                AddCardOption()
            }
        },
        sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = Color.Black.copy(alpha = 0.5f),
        content = {}
    )
}

@Composable
fun PaymentOption(title: String, iconRes: Int, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 16.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(17.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = { },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF32B768),
                    unselectedColor = Color.Gray,
                    disabledColor = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun AddCardOption() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_park_outline_add),
                contentDescription = "Add",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Add card", style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Add",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun PromoCodeBottomSheet(onDismiss: () -> Unit) {
    var promoCode by remember { mutableStateOf("") }

    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Enter promo code", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = promoCode,
                    onValueChange = { promoCode = it },
                    label = { Text("Promo code") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                ConfirmButton(isEnabled = true, onClick = {}, title = "Apply")
            }
        },
        sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = Color.Black.copy(alpha = 0.5f),
        content = {}
    )
}


@Preview(showBackground = true)
@Composable
private fun BookingScreenPreview() {
//    BookingConfirmationScreen(viewModel = BookingViewModel())
}

@Preview(showBackground = true)
@Composable
private fun PaymentPreview() {
    Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)) {
        Text(
            text = "Payment methods",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        PaymentOption("Cash", R.drawable.cash_pic, isSelected = true)
        Spacer(modifier = Modifier.height(10.dp))
        PaymentOption(
            "UzCard **** **** **** 0961",
            R.drawable.uzcard,
            isSelected = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        AddCardOption()
    }
}

@Preview(showBackground = true)
@Composable
private fun PromocodePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        Text(
            "Enter promo code",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 22.sp,
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
//        OutlinedTextField(
//            value = "sdjhjshdjhsd",
//            onValueChange = { promoCode = it },
//            label = { Text("Promo code") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//        )
//        ConfirmButton(isEnabled = true, onClick = {}, title = "Apply")
    }
}

