package com.bron24.bron24_android.screens.booking.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookingConfirmationViewModel @Inject constructor(
    private val model: BookingConfirmationModel
) : ViewModel() {

    private val _booking = MutableStateFlow<Booking?>(null)
    val booking = _booking.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun getBookingInfo(
        venueId: Int,
        date: String,
        sector: String,
        timeSlots: List<TimeSlot>
    ) {
        _isLoading.value = true
        val formattedTimeSlots: List<String> = timeSlots.map { timeSlot ->
            "${formatTime(timeSlot.startTime)} - ${formatTime(timeSlot.endTime)}"
        }
        try {
            val bookingDetails =
                model.fetchBookingDetails(venueId, date, sector, formattedTimeSlots)
            _booking.value = bookingDetails
        } catch (e: Exception) {
            // Handle error (e.g., show a message or log the error)
        } finally {
            _isLoading.value = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun formatTime(time: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val parsedTime = LocalTime.parse(time, inputFormatter)
        return parsedTime.format(outputFormatter)
    }
}