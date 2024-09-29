package com.bron24.bron24_android.screens.booking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.DateItem
import com.bron24.bron24_android.domain.entity.booking.Sector
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.screens.booking.states.BookingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@HiltViewModel
class BookingViewModel @Inject constructor(
    private val model: BookingModel
) : ViewModel() {
    private val _selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedDate: StateFlow<Long> = _selectedDate.asStateFlow()

    private val _selectedSector = MutableStateFlow<Sector?>(null)
    val selectedSector: StateFlow<Sector?> = _selectedSector.asStateFlow()

    private val _availableTimeSlots = MutableStateFlow<List<TimeSlot>>(emptyList())
    val availableTimeSlots: StateFlow<List<TimeSlot>> = _availableTimeSlots.asStateFlow()

    private val _availableDates = MutableStateFlow<List<DateItem>>(emptyList())
    val availableDates: StateFlow<List<DateItem>> = _availableDates.asStateFlow()

    private val _visibleMonthYear = MutableStateFlow("")
    val visibleMonthYear: StateFlow<String> = _visibleMonthYear.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    private val _getAvailableTimesState = MutableStateFlow<BookingState>(BookingState.Idle)
    val getAvailableTimesState: StateFlow<BookingState> = _getAvailableTimesState.asStateFlow()

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice.asStateFlow()

    private val _pricePerHour = MutableStateFlow(0)
    val pricePerHour: StateFlow<Int> = _pricePerHour.asStateFlow()

    fun selectDate(timestamp: Long) {
        _selectedDate.value = timestamp
    }

    fun selectSector(sector: Sector) {
        _selectedSector.value = sector
    }

    fun setPricePerHour(price: Int) {
        _pricePerHour.value = price
    }

    fun getAvailableTimes(venueId: Int) {
        viewModelScope.launch {
            _getAvailableTimesState.value = BookingState.Loading // Set state to Loading
            try {
                val selectedLocalDate = LocalDate.ofInstant(
                    Instant.ofEpochMilli(selectedDate.value),
                    ZoneId.systemDefault()
                )
                val formattedDate = selectedLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                val times = model.getAvailableTimeSlots(
                    venueId = venueId,
                    date = formattedDate,
                    sector = selectedSector.value?.name ?: "X" // Default value or handle appropriately
                )
                _availableTimeSlots.value = times
                _getAvailableTimesState.value = BookingState.Success // Update state to Success
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching available times", e)
                _availableTimeSlots.value = emptyList()
                _getAvailableTimesState.value = BookingState.Error(e.message ?: "Unknown Error") // Update to Error state
            }
        }
    }

    fun selectTimeSlot(timeSlot: TimeSlot) {
        val updatedTimeSlots = _availableTimeSlots.value.map { slot ->
            if (slot == timeSlot) {
                slot.copy(isSelected = !slot.isSelected)
            } else {
                slot
            }
        }
        _availableTimeSlots.value = updatedTimeSlots
        calculateTotalPrice()
    }

    private fun calculateTotalPrice() {
        val selectedTimeSlots = _availableTimeSlots.value.filter { it.isSelected }
        val totalHours = selectedTimeSlots.size // Assuming each time slot is 1 hour
        _totalPrice.value = totalHours * _pricePerHour.value
    }

    fun showDatePicker() {
        _showDatePicker.value = true
    }

    fun onDatePickerShown() {
        _showDatePicker.value = false
    }

    fun updateVisibleMonthYear(dateItem: DateItem) {
        val localDate = LocalDate.ofInstant(Instant.ofEpochMilli(dateItem.timestamp), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
        _visibleMonthYear.value = localDate.format(formatter)
    }

    fun formatPrice(price: String): String {
        return try {
            val floatPrice = price.toFloat()
            val intPrice = floatPrice.toInt()
            String.format("%,d", intPrice).replace(",", " ")
        } catch (e: NumberFormatException) {
            "0" // Return "0" if parsing fails
        }
    }
}