package com.bron24.bron24_android.core.domain.usecases

import com.bron24.bron24_android.core.domain.entities.Venue
import com.bron24.bron24_android.core.domain.repository.VenueRepository
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    suspend operator fun invoke(): List<Venue> {
        return repository.getVenues()
    }
}