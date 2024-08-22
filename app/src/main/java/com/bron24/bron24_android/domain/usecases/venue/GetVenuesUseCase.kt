package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    suspend fun execute(): List<Venue> = coroutineScope {
        val venues = repository.getVenues()
        venues.map { venue ->
            async {
                val pictures = repository.getVenuePictures(venue.venueId)
                venue.copy(imageUrls = pictures)
            }
        }.awaitAll()
    }
}