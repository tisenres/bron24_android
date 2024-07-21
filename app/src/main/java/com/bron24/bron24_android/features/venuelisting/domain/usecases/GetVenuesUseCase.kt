package com.bron24.bron24_android.features.venuelisting.domain.usecases

import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue
import com.bron24.bron24_android.features.venuelisting.domain.repository.VenueRepository
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    suspend operator fun invoke(): List<Venue> {
        return repository.getVenues()
    }
}