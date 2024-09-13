package com.bron24.bron24_android.screens.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.BookingStatus
import com.bron24.bron24_android.domain.entity.booking.DateItem
import com.bron24.bron24_android.domain.entity.venue.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {
    private val _selectedDate = MutableStateFlow<Long>(System.currentTimeMillis())
    val selectedDate: StateFlow<Long> = _selectedDate.asStateFlow()

    private val _availableDates = MutableStateFlow<List<DateItem>>(emptyList())
    val availableDates: StateFlow<List<DateItem>> = _availableDates.asStateFlow()

    private val _visibleMonthYear = MutableStateFlow<String>("")
    val visibleMonthYear: StateFlow<String> = _visibleMonthYear.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    private val _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState: StateFlow<BookingState> = _bookingState.asStateFlow()

    private val _selectedStadiumPart = MutableStateFlow<String?>(null)
    val selectedStadiumPart: StateFlow<String?> = _selectedStadiumPart.asStateFlow()

    private val _selectedTime = MutableStateFlow<Long?>(null)
    val selectedTime: StateFlow<Long?> = _selectedTime.asStateFlow()

    private val _availableStadiumParts = MutableStateFlow(listOf("A", "B"))
    val availableStadiumParts: StateFlow<List<String>> = _availableStadiumParts.asStateFlow()

    private val _availableTimes = MutableStateFlow<List<Long>>(emptyList())
    val availableTimes: StateFlow<List<Long>> = _availableTimes.asStateFlow()

    private val _booking = MutableStateFlow(
        Booking(
            id = "1",
            venueId = 1,
            userId = "user123",
            startTime = System.currentTimeMillis(),
            endTime = System.currentTimeMillis() + 7200000, // 2 hours later
            venueName = "Bunyodkor kompleksi",
            address = Address(
                id = 9,
                addressName = "Tashkent",
                district = "Chilanzar",
                closestMetroStation = "Novza"
            ),
            date = System.currentTimeMillis(),
            status = BookingStatus.PENDING,
            stadiumPart = "Sector A",
            fullName = "Cristiano Ronaldo",
            firstNumber = "+998 90 900 90 90",
            secondNumber = "+998",
            totalPrice = "100 000 sum"
        )
    )
    val booking: StateFlow<Booking> = _booking

    init {
        generateDateItems()
        generateAvailableTimes()
    }

    private fun generateAvailableTimes() {
        _availableTimes.value = listOf(
            32400000L, // 9:00 AM
            36000000L, // 10:00 AM
            39600000L, // 11:00 AM
            43200000L, // 12:00 PM
            46800000L, // 1:00 PM
            50400000L, // 2:00 PM
            54000000L, // 3:00 PM
            57600000L  // 4:00 PM
        )
    }

    fun createBooking(venueId: Int, userId: String) {
        val stadiumPart = _selectedStadiumPart.value
        val date = _selectedDate.value
        val time = _selectedTime.value

        if (stadiumPart == null || time == null) {
            _bookingState.value = BookingState.Error("Please select all required fields")
            return
        }

        viewModelScope.launch {
            _bookingState.value = BookingState.Loading
            try {
                // Simulate API call delay
                delay(1000)

                val booking = Booking(
                    id = generateMockId(),
                    venueId = venueId,
                    userId = userId,
                    startTime = date + time,
                    endTime = date + time + 3600000, // Assuming 1 hour booking
                    stadiumPart = stadiumPart,
                    secondNumber = "",
                    totalPrice = "727636723",
                    fullName = "SJHSJHDHJSDJH",
                    status = BookingStatus.PENDING,
                    firstNumber = "27372737623",
                    venueName = "SHJDJHSDHJHSJD",
                    address = Address(5, "SDJUSUDYUYSD", "syysys", "yyyy"),
                    date = date
                )
                _bookingState.value = BookingState.Success(booking)
            } catch (e: Exception) {
                _bookingState.value = BookingState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun confirmBooking(booking: Booking) {
        viewModelScope.launch {
            _bookingState.value = BookingState.Loading
            delay(2000) // Simulate network call
            _bookingState.value = BookingState.Success(booking)
        }
    }

    fun selectDate(timestamp: Long) {
        _selectedDate.value = timestamp
        _availableDates.value = _availableDates.value.map { it.copy(isSelected = it.timestamp == timestamp) }
        _availableDates.value.find { it.timestamp == timestamp }?.let { updateVisibleMonthYear(it) }
        fetchTimeSlots()

        // Update available dates to only include dates in the same month as the selected date
        val selectedCalendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        val currentMonth = selectedCalendar.get(Calendar.MONTH)
        val currentYear = selectedCalendar.get(Calendar.YEAR)

        _availableDates.value = _availableDates.value.filter { dateItem ->
            val itemCalendar = Calendar.getInstance().apply { timeInMillis = dateItem.timestamp }
            itemCalendar.get(Calendar.MONTH) == currentMonth && itemCalendar.get(Calendar.YEAR) == currentYear
        }
    }

    private fun generateDateItems() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val dateItems = mutableListOf<DateItem>()
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            while (calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear) {
                val timestamp = calendar.timeInMillis
                dateItems.add(
                    DateItem(
                        day = calendar.get(Calendar.DAY_OF_MONTH),
                        dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time),
                        month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time),
                        year = calendar.get(Calendar.YEAR),
                        isSelected = timestamp == _selectedDate.value,
                        timestamp = timestamp
                    )
                )
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
            _availableDates.value = dateItems
            updateVisibleMonthYear(dateItems.first())
        }
    }


    fun selectStadiumPart(part: String) {
        _selectedStadiumPart.value = part
        fetchTimeSlots()
    }

    fun selectTime(time: Long) {
        _selectedTime.value = time
    }

    fun updateVisibleMonthYear(dateItem: DateItem) {
        _visibleMonthYear.value = "${dateItem.month} ${dateItem.year}"
    }

    fun fetchTimeSlots() {
        // In a real app, this would fetch time slots from an API
        // For now, we'll just use the static list of available times
        // You could implement logic here to filter times based on selected date and stadium part
    }

    fun showDatePicker() {
        _showDatePicker.value = true
    }

    fun onDatePickerShown() {
        _showDatePicker.value = false
    }

    private fun generateMockId(): String {
        return "BOOKING-${System.currentTimeMillis()}"
    }
}