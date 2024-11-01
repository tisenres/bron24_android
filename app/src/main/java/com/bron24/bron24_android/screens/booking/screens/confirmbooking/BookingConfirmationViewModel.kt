package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BookingConfirmationViewModel @Inject constructor(
    private val model: BookingConfirmationModel
) : ViewModel() {

    private val _booking = MutableStateFlow<Booking?>(null)
    val booking = _booking.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading

    private val _secondPhoneNumber = MutableStateFlow("")
    val secondPhoneNumber: StateFlow<String> get() = _secondPhoneNumber

    private val _isBookingConfirmed = MutableStateFlow<Boolean?>(null)
    val isBookingConfirmed: StateFlow<Boolean?> get() = _isBookingConfirmed

    private val _isPhoneNumberValid = MutableStateFlow(false)
    val isPhoneNumberValid: StateFlow<Boolean> = _isPhoneNumberValid

    suspend fun getBookingInfo(
        venueId: Int,
        date: String,
        sector: String,
        timeSlots: List<TimeSlot>
    ) {
        _isLoading.value = true
        try {
            val bookingDetails =
                model.fetchBookingDetails(venueId, date, sector, timeSlots)
            _booking.value = bookingDetails
        } catch (e: Exception) {
            // Handle error (e.g., show a message or log the error)
        } finally {
            _isLoading.value = false
            Log.d("BookingConfirmationViewModel", booking.value.toString())
        }
    }

    fun formatDate(inputDate: String): String {
        // Define the input and output date formats
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        // Parse the input date and format it to the desired output
        val parsedDate = inputFormatter.parse(inputDate)
        return outputFormatter.format(parsedDate)
    }

//    fun formatTimeSlot(startTime: String, endTime: String): String {
//         Define the input format that includes hours, minutes, and seconds
//        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//
//         Define the output format that includes only hours and minutes
//        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//
//         Parse the start and end time strings
//        val parsedStartTime = inputFormat.parse(startTime)
//        val parsedEndTime = inputFormat.parse(endTime)
//
//         Format the parsed times to the desired output format
//        return "${outputFormat.format(parsedStartTime!!)} - ${outputFormat.format(parsedEndTime!!)}"
//    }

    fun updatePhoneNumber(phone: String) {
        _secondPhoneNumber.value = phone
        _isPhoneNumberValid.value = isValidUzbekPhoneNumber(phone)
    }

    private fun isValidUzbekPhoneNumber(phone: String): Boolean {
        val regex = "^\\+998[0-9]{9}$".toRegex()
        return regex.matches(phone)
    }

    fun confirmBooking() {
        viewModelScope.launch {
            val orderId = model.confirmBooking()

            if (orderId != null) {
                ToastManager.showToast("Booking confirmed", ToastType.SUCCESS)
                _isBookingConfirmed.value = true
            } else {
                ToastManager.showToast("Booking confirmation failed", ToastType.ERROR)
                _isBookingConfirmed.value = false
            }
        }
    }
}