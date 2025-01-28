package com.bron24.bron24_android.screens.booking.screens.startbooking

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.DateItem
import com.bron24.bron24_android.domain.entity.booking.Sector
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.helper.util.formatPrice
import com.bron24.bron24_android.screens.booking.states.BookingState
import com.google.android.play.integrity.internal.m
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Month
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val model: BookingModel
) : ViewModel() {
    private val _selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedDate: StateFlow<Long> = _selectedDate.asStateFlow()

    private var countTimeSlot: Int = 0

    private val _selectedDateIndex = MutableStateFlow(-1)
    val selectedDateIndex: StateFlow<Int> = _selectedDateIndex.asStateFlow()
    private val _checkSelected = MutableStateFlow(false)
    val checkSelected:StateFlow<Boolean> = _checkSelected

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

    private val _totalPrice = MutableStateFlow("0")
    val totalPrice: StateFlow<String> = _totalPrice.asStateFlow()

    private val _pricePerHour = MutableStateFlow(0)
    val pricePerHour: StateFlow<Int> = _pricePerHour.asStateFlow()

    private val _selectedTimeSlots = MutableStateFlow<Set<TimeSlot>>(emptySet())
    val selectedTimeSlots: StateFlow<Set<TimeSlot>> = _selectedTimeSlots.asStateFlow()

    fun selectDate(timestamp: Long) {
        _selectedDate.value = roundToStartOfDay(timestamp)
        updateSelectedDate(timestamp)
        updateVisibleMonthYear(timestamp)
        Log.d("BookingViewModel", "Selected Date: ${_selectedDate.value}")
        Log.d("BookingViewModel", "Selected Date Index: ${_selectedDateIndex.value}")
    }

    private fun updateSelectedDate(selectedTimestamp: Long) {
        val roundedTimestamp = roundToStartOfDay(selectedTimestamp)
        val updatedDates = _availableDates.value.map { dateItem ->
            val isSelected = roundToStartOfDay(dateItem.timestamp) == roundedTimestamp
            dateItem.copy(isSelected = isSelected)
        }

        _availableDates.value = updatedDates
        _selectedDateIndex.value = updatedDates.indexOfFirst { it.isSelected }
    }

    fun selectSector(sector: Sector) {
        _selectedSector.value = sector
    }

    fun setPricePerHour(price: Int) {
        _pricePerHour.value = price
    }

    fun getAvailableTimes(venueId: Int) {
        viewModelScope.launch {
            _getAvailableTimesState.value = BookingState.Loading
            try {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = selectedDate.value
                }

                // Use SimpleDateFormat to format the date
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = formatter.format(calendar.time)

                // Fetch available time slots
                val times = model.getAvailableTimeSlots(
                    venueId = venueId,
                    date = formattedDate,
                    sector = selectedSector.value?.name
                        ?: "X"
                )

                _availableTimeSlots.value = times
                _getAvailableTimesState.value = BookingState.Success // Update state to Success
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching available times", e)
                _availableTimeSlots.value = emptyList()
                _getAvailableTimesState.value =
                    BookingState.Error(e.message ?: "Unknown Error") // Update to Error state
            }
        }
    }

    fun generateAvailableDates() {
        val calendar = Calendar.getInstance()
        val today = roundToStartOfDay(calendar.timeInMillis)
        val dateList = mutableListOf<DateItem>()

        for (i in 0 until 30) {
            val date = calendar.time
            val roundedTimestamp = roundToStartOfDay(date.time)

            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
            val month = SimpleDateFormat("MMMM", Locale.getDefault()).format(date)
            val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(date).toInt()
            val day = SimpleDateFormat("d", Locale.getDefault()).format(date).toInt()

            dateList.add(
                DateItem(
                    day = day,
                    dayOfWeek = dayOfWeek,
                    month = month,
                    year = year,
                    isSelected = roundedTimestamp == today,
                    timestamp = roundedTimestamp
                )
            )

            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        _availableDates.value = dateList
    }

    fun selectTimeSlot(timeSlot: TimeSlot) {
        val updatedTimeSlots = _availableTimeSlots.value.map {
            if (it == timeSlot) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }

        _availableTimeSlots.value = updatedTimeSlots

        _selectedTimeSlots.value = updatedTimeSlots.filter { it.isSelected }.toSet()

        calculateTotalPrice()
    }

    private fun calculateTotalPrice() {
        val totalHours = _selectedTimeSlots.value.size // Assuming each time slot is 1 hour
        _totalPrice.value = (totalHours * _pricePerHour.value).toString().formatPrice()
    }

    fun showDatePicker() {
        _showDatePicker.value = true
    }

    fun onDatePickerDismissed() {
        _showDatePicker.value = false
    }

    private fun updateVisibleMonthYear(timestamp: Long) {
        val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }

        val formattedDate = formatter.format(calendar.time)

        _visibleMonthYear.value = formattedDate
    }

    private fun getMonthNumber(monthName: String): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Month.valueOf(monthName.uppercase()).value
        } else {
            Month.valueOf(monthName.uppercase()).ordinal + 1
        }
    }

    private fun roundToStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}