package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import javax.inject.Inject

class GetVenueDetailsUseCase @Inject constructor(
    private val venueRepository: VenueRepository
) {
    suspend fun execute(venueId: Int): VenueDetails? {
        return try {
            val venue = venueRepository.getVenueDetailsById(venueId)
            val pictures = venueRepository.getVenuePictures(venueId)
            venue?.copy(imageUrls = pictures)
        } catch (e: Exception) {
            // Log the error if needed
            null
        }
    }
}