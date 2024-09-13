package com.bron24.bron24_android.screens.booking

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.helper.extension.DateTimeFormatter.formatDate
import com.bron24.bron24_android.helper.extension.DateTimeFormatter.formatTimeRange
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun BookingConfirmationScreen(viewModel: BookingViewModel) {
    val bookingInfo by viewModel.booking.collectAsState()
    var showPaymentMethods by remember { mutableStateOf(false) }
    var showPromoCode by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            TopAppBar()
            BookingInfoCard(bookingInfo)
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodButton { showPaymentMethods = true }
            Spacer(modifier = Modifier.height(16.dp))
            PromoCodeButton { showPromoCode = true }
            Spacer(modifier = Modifier.height(16.dp))
            TotalAmount(bookingInfo.totalPrice)
            Spacer(modifier = Modifier.weight(1f))
            ConfirmButton(true, onClick = { /* Handle confirm button click */ })
        }

        if (showPaymentMethods) {
            PaymentMethodsBottomSheet { showPaymentMethods = false }
        }

        if (showPromoCode) {
            PromoCodeBottomSheet { showPromoCode = false }
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
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
            VenueInfo(booking.venueName, booking.address.toString())
            BookingDetail("DATE", formatDate(booking.date))
            BookingDetail("TIME", formatTimeRange(booking.startTime, booking.endTime))
            BookingDetail("STADIUM PART", booking.stadiumPart)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            BookingDetail("FULL NAME", booking.fullName)
            BookingDetail("FIRST NUMBER", booking.firstNumber)
            booking.secondNumber?.let { BookingDetail("SECOND NUMBER", it) }
        }
    }
}


@Composable
fun VenueInfo(venue: String, address: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
                text = "Mustaqillik maydoni, Chilanzar, Tashkent",
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
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.payment_method),
                    contentDescription = "Payment",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Choose Payment", fontSize = 16.sp)
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Choose",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun PromoCodeButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.promo_code),
                    contentDescription = "Promo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Enter promo code", fontSize = 16.sp)
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Enter",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun TotalAmount(total: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Total", fontSize = 16.sp, color = Color.Gray)
        Text(total, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ConfirmButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (isEnabled) Color(0xFF26A045) else Color(0xFFE4E4E4),
        contentColor = Color.White,
        disabledContainerColor = Color(0xFFE4E4E4),
        disabledContentColor = Color.Gray
    )

    Button(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.next),
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Payment methods", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                PaymentOption("Cash", R.drawable.cash_icon, isSelected = true)
                PaymentOption(
                    "UzCard **** **** **** 0961",
                    R.drawable.cash_icon,
                    isSelected = false
                )
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(selected = isSelected, onClick = { /* Handle selection */ })
    }
}

@Composable
fun AddCardOption() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add card",
            tint = Color(0xFF32B768)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Add card", fontSize = 16.sp, color = Color(0xFF32B768))
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Add",
            tint = Color.Gray
        )
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
                Button(
                    onClick = { /* Handle promo code application */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF32B768))
                ) {
                    Text("Apply", color = Color.White)
                }
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
    BookingConfirmationScreen(viewModel = hiltViewModel())
}