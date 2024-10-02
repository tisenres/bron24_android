package com.bron24.bron24_android.screens.booking.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookingConfirmationViewModel @Inject constructor(
    private val model: BookingConfirmationModel

): ViewModel() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun getBookingInfo(venueId: Int, date: String, sector: String, timeSlots: List<TimeSlot>): Booking {
        val formattedTimeSlots: List<String> = timeSlots.map { timeSlot ->
            "${formatTime(timeSlot.startTime)} - ${formatTime(timeSlot.endTime)}"
        }
        return model.fetchBookingDetails(venueId, date, sector, formattedTimeSlots)
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun formatTime(time: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val parsedTime = LocalTime.parse(time, inputFormatter)
    return parsedTime.format(outputFormatter)
}