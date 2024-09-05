package com.bron24.bron24_android.screens.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val bookingModel: BookingModel) : ViewModel() {

    private val _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState: StateFlow<BookingState> = _bookingState.asStateFlow()

    private val _selectedStadiumPart = MutableStateFlow<String?>(null)
    val selectedStadiumPart: StateFlow<String?> = _selectedStadiumPart.asStateFlow()

    private val _selectedDate = MutableStateFlow<Long?>(null)
    val selectedDate: StateFlow<Long?> = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow<Long?>(null)
    val selectedTime: StateFlow<Long?> = _selectedTime.asStateFlow()

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
                val booking = Booking(
                    venueId = venueId,
                    userId = userId,
                    startTime = date + time,
                    endTime = date + time + 3600000 // Assuming 1 hour booking
                )
                val result = bookingModel.createBooking(booking)
                _bookingState.value = BookingState.Success(result)
            } catch (e: Exception) {
                _bookingState.value = BookingState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
