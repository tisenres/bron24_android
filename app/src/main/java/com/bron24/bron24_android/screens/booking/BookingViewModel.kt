package com.bron24.bron24_android.screens.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@HiltViewModel
class BookingViewModel @Inject constructor(
    private val model: BookingModel
) : ViewModel() {
    private val _selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedDate: StateFlow<Long> = _selectedDate.asStateFlow()

    private val _selectedStadiumPart = MutableStateFlow<String?>(null)
    val selectedStadiumPart: StateFlow<String?> = _selectedStadiumPart.asStateFlow()

    private val _availableTimeSlots = MutableStateFlow<List<TimeSlot>>(emptyList())
    val availableTimeSlots: StateFlow<List<TimeSlot>> = _availableTimeSlots.asStateFlow()

    private val _venueId = MutableStateFlow(0)

    fun initializeBooking(venueId: Int, sectors: List<String>) {
        _venueId.value = venueId
        if (sectors.isNotEmpty()) {
            selectStadiumPart(sectors.first())
        }
        fetchAvailableTimeSlots()
    }

    fun selectDate(timestamp: Long) {
        _selectedDate.value = timestamp
        fetchAvailableTimeSlots()
    }

    fun selectStadiumPart(part: String) {
        _selectedStadiumPart.value = part
        fetchAvailableTimeSlots()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun fetchAvailableTimeSlots() {
        viewModelScope.launch {
            val currentVenueId = _venueId.value
            val date = _selectedDate.value
            val sector = _selectedStadiumPart.value ?: return@launch

            if (currentVenueId == 0 || sector.isEmpty()) {
                return@launch
            }

            val formattedDate = formatDate(date)
            val timeSlots = model.getAvailableTimeSlots(currentVenueId, formattedDate, sector)
            _availableTimeSlots.value = timeSlots
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun formatDate(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault())
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}