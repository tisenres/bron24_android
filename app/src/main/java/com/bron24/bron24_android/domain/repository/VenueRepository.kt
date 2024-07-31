package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails

interface VenueRepository {
    suspend fun getVenues(): List<Venue>
    suspend fun getVenuesCoordinates(): List<VenueCoordinates>
    suspend fun getVenuePictures(venueId: Int): List<String>
    suspend fun getVenueDetailsById(venueId: String): VenueDetails
}
