package com.bron24.bron24_android.screens.venuedetails.history

import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenueDetailsModel @Inject constructor(
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase
) {
    fun getVenueDetails(venueId: Int): Flow<VenueDetails> {
        return getVenueDetailsUseCase.invoke(venueId)
    }
}