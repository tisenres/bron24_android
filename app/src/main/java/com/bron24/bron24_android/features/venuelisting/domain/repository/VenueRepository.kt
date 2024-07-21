package com.bron24.bron24_android.features.venuelisting.domain.repository

import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue

interface VenueRepository {
    suspend fun getVenues(): List<Venue>
}
