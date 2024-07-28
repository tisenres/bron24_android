package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.venue.Venue

interface VenueRepository {
    suspend fun getVenues(): List<Venue>
//    fun getVenueById(venueId: String): Venue
//    fun searchVenues(query: String): List<Venue>
}
