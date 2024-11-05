package com.bron24.bron24_android.screens.venuedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.catch

@HiltViewModel
class VenueDetailsViewModel @Inject constructor(
    private val model: VenueDetailsModel
) : ViewModel() {

    private val _venueDetailsState = MutableStateFlow<VenueDetailsState>(VenueDetailsState.Initial)
    val venueDetailsState: StateFlow<VenueDetailsState> = _venueDetailsState

    fun fetchVenueDetails(venueId: Int) {
        viewModelScope.launch {
            _venueDetailsState.value = VenueDetailsState.Loading
            model.getVenueDetails(venueId)
                .catch { e ->
                    Log.e("VenueDetailsViewModel", "Error fetching venue details", e)
                    _venueDetailsState.value = VenueDetailsState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { details ->
                    _venueDetailsState.value = VenueDetailsState.Success(details)
                }
        }
    }
}

sealed class VenueDetailsState {
    object Initial : VenueDetailsState()
    object Loading : VenueDetailsState()
    data class Success(val venueDetails: VenueDetails) : VenueDetailsState()
    data class Error(val message: String) : VenueDetailsState()
}