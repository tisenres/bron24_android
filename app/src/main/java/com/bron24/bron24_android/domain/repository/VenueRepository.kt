package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates

interface VenueRepository {
    suspend fun getVenues(): List<Venue>
    suspend fun getVenuesCoordinates(): List<VenueCoordinates>
//    fun getVenueById(venueId: String): Venue
//    fun searchVenues(query: String): List<Venue>
}
