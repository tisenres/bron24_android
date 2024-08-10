package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService
) : VenueRepository {
    override suspend fun getVenues(): List<Venue> {
        return apiService.getVenues().map { it.toDomainModel() }
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> {
        return apiService.getVenuesCoordinates().map { it.toDomainModel() }
    }

    override suspend fun getVenuePictures(venueId: Int): List<String> {
        return apiService.getVenuePictures(venueId).map { it.url }
    }

    override suspend fun getVenueDetailsById(venueId: Int): VenueDetails {
        return apiService.getVenueDetails(venueId).toDomainModel()
    }
}

