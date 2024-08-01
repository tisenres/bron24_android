package com.bron24.bron24_android.screens.venuedetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VenueDetailsViewModel @Inject constructor(
    private val venueDetailsModel: VenueDetailsModel
): ViewModel() {

}