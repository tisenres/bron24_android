package com.bron24.bron24_android.screens.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {

    private val _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState: StateFlow<BookingState> = _bookingState.asStateFlow()

    private val _selectedStadiumPart = MutableStateFlow<String?>(null)
    val selectedStadiumPart: StateFlow<String?> = _selectedStadiumPart.asStateFlow()

    private val _selectedDate = MutableStateFlow<Long?>(null)
    val selectedDate: StateFlow<Long?> = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow<Long?>(null)
    val selectedTime: StateFlow<Long?> = _selectedTime.asStateFlow()

    // Mock data
    private val stadiumParts = listOf("A", "B")
    val availableDates = listOf(
        Date().time,
        Date().time + 86400000,
        Date().time + 172800000,
        Date().time + 259200000,
        Date().time + 345600000
    )
    val availableTimes = listOf(
        32400000L, // 9:00 AM
        36000000L, // 10:00 AM
        39600000L, // 11:00 AM
        43200000L, // 12:00 PM
        46800000L, // 1:00 PM
        50400000L, // 2:00 PM
        54000000L, // 3:00 PM
        57600000L  // 4:00 PM
    )

    private val _availableStadiumParts = MutableStateFlow(stadiumParts)
    val availableStadiumParts: StateFlow<List<String>> = _availableStadiumParts.asStateFlow()

//    private val _availableDates = MutableStateFlow(availableDates)
//    val availableDates: StateFlow<List<Long>> = _availableDates.asStateFlow()
//
//    private val _availableTimes = MutableStateFlow(availableTimes)
//    val availableTimes: StateFlow<List<Long>> = _availableTimes.asStateFlow()

    fun selectStadiumPart(part: String) {
        _selectedStadiumPart.value = part
    }

    fun selectDate(date: Long) {
        _selectedDate.value = date
    }

    fun selectTime(time: Long) {
        _selectedTime.value = time
    }

    fun createBooking(venueId: Int, userId: String) {
        val stadiumPart = _selectedStadiumPart.value
        val date = _selectedDate.value
        val time = _selectedTime.value

        if (stadiumPart == null || date == null || time == null) {
            _bookingState.value = BookingState.Error("Please select all required fields")
            return
        }

        viewModelScope.launch {
            _bookingState.value = BookingState.Loading
            try {
                // Simulate API call delay
                kotlinx.coroutines.delay(1000)

                val booking = Booking(
                    id = generateMockId(),
                    venueId = venueId,
                    userId = userId,
                    startTime = date + time,
                    endTime = date + time + 3600000, // Assuming 1 hour booking
                    stadiumPart = stadiumPart
                )
                _bookingState.value = BookingState.Success(booking)
            } catch (e: Exception) {
                _bookingState.value = BookingState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun generateMockId(): String {
        return "BOOKING-${System.currentTimeMillis()}"
    }
}
