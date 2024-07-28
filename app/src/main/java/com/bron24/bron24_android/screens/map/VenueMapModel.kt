package com.bron24.bron24_android.screens.map

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import javax.inject.Inject

class VenueMapModel @Inject constructor(
    private val getVenuesUseCase: GetVenuesUseCase
) {
    suspend fun getVenuesUseCase(): List<Venue> {
        return getVenuesUseCase.execute()
    }
}