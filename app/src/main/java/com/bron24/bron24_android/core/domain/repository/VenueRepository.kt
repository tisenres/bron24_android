package com.bron24.bron24_android.core.domain.repository

import com.bron24.bron24_android.core.domain.entities.Venue

interface VenueRepository {
    suspend fun getVenues(): List<Venue>
}
