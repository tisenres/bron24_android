package com.bron24.bron24_android.screens.venuedetails

import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import javax.inject.Inject

class VenueDetailsModel @Inject constructor(
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase
) {

}