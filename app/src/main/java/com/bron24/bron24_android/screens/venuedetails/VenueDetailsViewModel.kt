package com.bron24.bron24_android.screens.venuedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueDetailsViewModel @Inject constructor(
    private val model: VenueDetailsModel
): ViewModel() {

    private val _venueDetails = MutableStateFlow<VenueDetails?>(null)
    val venueDetails: StateFlow<VenueDetails?> = _venueDetails

    fun fetchVenueDetails(venueId: Int) {
        viewModelScope.launch {
            val details = model.getVenueDetails(venueId)
            _venueDetails.value = details
        }
    }
}
