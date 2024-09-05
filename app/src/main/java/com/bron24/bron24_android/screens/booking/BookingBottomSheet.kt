package com.bron24.bron24_android.screens.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingBottomSheet(
    viewModel: BookingViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onNavigateToSuccess: () -> Unit
) {
    val bookingState by viewModel.bookingState.collectAsState()
    val selectedStadiumPart by viewModel.selectedStadiumPart.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()

    LaunchedEffect(bookingState) {
        if (bookingState is BookingState.Success) {
            onNavigateToSuccess()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Stadium Part",
                style = MaterialTheme.typography.headlineSmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StadiumPartButton("Sector A", selectedStadiumPart == "Sector A") {
                    viewModel.selectStadiumPart("Sector A")
                }
                StadiumPartButton("Sector B", selectedStadiumPart == "Sector B") {
                    viewModel.selectStadiumPart("Sector B")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Date",
                style = MaterialTheme.typography.headlineSmall
            )
            // Implement date selection UI here
            // When a date is selected, call viewModel.selectDate(selectedDateInMillis)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Available times",
                style = MaterialTheme.typography.headlineSmall
            )
            // Implement time selection UI here
            // When a time is selected, call viewModel.selectTime(selectedTimeInMillis)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.createBooking(venueId = 1, userId = "user123")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Order")
            }

            when (val state = bookingState) {
                is BookingState.Loading -> CircularProgressIndicator()
                is BookingState.Error -> Text(state.message, color = Color.Red)
                else -> {}
            }
        }
    }
}

@Composable
fun StadiumPartButton(s: String, b: Boolean, content: @Composable () -> Unit) {

}
