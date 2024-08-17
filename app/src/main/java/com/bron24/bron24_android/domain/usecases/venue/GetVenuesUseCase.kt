package com.bron24.bron24_android.domain.usecases.venue

import android.util.Log
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    suspend fun execute(): List<Venue> {
        val venues = repository.getVenues()
        val venuesWithPictures = venues.map { venue ->
            Log.d("SDGHSHGDGHDGHHDG", "loading venues.........")
            val pictures = repository.getVenuePictures(venue.venueId)
            venue.copy(imageUrls = pictures)
        }
        Log.d("SDGHSHGDGHDGHHDG", "Venues from use case $venuesWithPictures")
        return venuesWithPictures
    }
}