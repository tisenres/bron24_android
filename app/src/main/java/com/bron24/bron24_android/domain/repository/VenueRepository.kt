package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
    fun getVenues(): Flow<List<Venue>>
    suspend fun refreshVenues()
    suspend fun getVenuesCoordinates(): List<VenueCoordinates>
    fun getVenuePicture(venueId: Int): Flow<String?>
    suspend fun getVenueDetailsById(venueId: Int): VenueDetails
}
