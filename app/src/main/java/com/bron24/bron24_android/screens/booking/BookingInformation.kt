package com.bron24.bron24_android.screens.booking

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bron24.bron24_android.domain.entity.booking.Booking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingInformationScreen(
    navController: NavController,
    booking: Booking,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val bookingState by viewModel.bookingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = { Text("Booking Information") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )

        BookingDetailsCard(booking)
        PaymentSection()
        PromoCodeSection()
        TotalSection(booking.totalPrice)
        ConfirmButton { viewModel.confirmBooking(booking) }
    }

    when (bookingState) {
        is BookingState.Loading -> LoadingIndicator()
        is BookingState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate("bookingConfirmation")
            }
        }
        is BookingState.Error -> ErrorDialog((bookingState as BookingState.Error).message)
        else -> {} // Idle state, do nothing
    }
}

@Composable
fun BookingDetailsCard(booking: Booking) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            BookingDetailItem("Venue", booking.venueName)
            BookingDetailItem("Address", booking.address.addressName)
            BookingDetailItem("Date", booking.date.toString())
            BookingDetailItem("Time", "${booking.startTime} - ${booking.endTime}")
            BookingDetailItem("Stadium Part", booking.stadiumPart)
            BookingDetailItem("Full Name", booking.fullName)
            BookingDetailItem("First Number", booking.firstNumber)
            BookingDetailItem("Second Number", booking.secondNumber ?: "Not provided")
        }
    }
}

@Composable
fun BookingDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

@Composable
fun PaymentSection() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Payment icon placeholder
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.LightGray, RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text("Choose Payment", fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.rotate(180f))
    }
}

@Composable
fun PromoCodeSection() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Promo code icon placeholder
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.LightGray, RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text("Enter promo code", fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.rotate(180f))
    }
}

@Composable
fun TotalSection(totalPrice: String) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Total", color = Color.Gray, fontSize = 14.sp)
        Text(totalPrice, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun ConfirmButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF32B768))
    ) {
        Text("Confirm", color = Color.White)
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorDialog(message: String) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Error") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = { }) {
                Text("OK")
            }
        }
    )
}