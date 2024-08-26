package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
    suspend fun getVenues(): List<Venue>
    suspend fun getVenuesCoordinates(): List<VenueCoordinates>
    suspend fun getVenuePicture(venueId: Int): String?
    suspend fun getVenueDetailsById(venueId: Int): VenueDetails
}
