package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.data.network.VenueApiService
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService
) : VenueRepository {
    override suspend fun getVenues(): List<Venue> {
        return apiService.getVenues().map { it.toDomainModel() }
    }
}

