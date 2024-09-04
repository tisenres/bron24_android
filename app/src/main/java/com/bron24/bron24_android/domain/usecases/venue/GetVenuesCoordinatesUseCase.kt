package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.repository.VenueRepository
import javax.inject.Inject

class GetVenuesCoordinatesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    suspend fun execute(): List<VenueCoordinates> {
        return try {
            repository.getVenuesCoordinates()
        } catch (e: Exception) {
            // Log the error if needed
            emptyList()
        }
    }
}