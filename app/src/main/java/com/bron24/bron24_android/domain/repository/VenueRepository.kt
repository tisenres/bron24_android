package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails

interface VenueRepository {
    suspend fun getVenues(
        latitude: Double? = null,
        longitude: Double? = null,
        sort: String? = null,
        availableTime: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        infrastructure: Boolean? = null,
        district: String? = null
    ): List<Venue>
    suspend fun getVenuesCoordinates(): List<VenueCoordinates>
    suspend fun getVenuePictures(venueId: Int): List<String>
    suspend fun getVenueDetailsById(venueId: Int, latitude: Double?, longitude: Double?): VenueDetails?
    suspend fun searchVenues(query: String?, latitude: Double?, longitude: Double?): List<Venue>
}
