package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService
) : VenueRepository {

    override suspend fun getVenues(
        latitude: Double?,
        longitude: Double?,
        sort: String?,
        availableTime: String?,
        minPrice: Int?,
        maxPrice: Int?,
        infrastructure: Boolean?,
        district: String?
    ): List<Venue> = withContext(Dispatchers.IO) {
        try {
            apiService.getVenues(
                latitude, longitude, sort, availableTime,
                minPrice, maxPrice, infrastructure, district
            )?.data?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            // Log the error if needed
            emptyList()
        }
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> = withContext(Dispatchers.IO) {
        try {
            apiService.getVenuesCoordinates()?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            // Log the error if needed
            emptyList()
        }
    }

    override suspend fun getFirstVenuePicture(venueId: Int): String? = withContext(Dispatchers.IO) {
        try {
            apiService.getVenuePictures(venueId)?.firstOrNull()?.url
        } catch (e: Exception) {
            // Log the error if needed
            null
        }
    }

    override suspend fun getVenuePictures(venueId: Int): List<String> = withContext(Dispatchers.IO) {
        try {
            apiService.getVenuePictures(venueId)?.map { it.url } ?: emptyList()
        } catch (e: Exception) {
            // Log the error if needed
            emptyList()
        }
    }

    override suspend fun getVenueDetailsById(venueId: Int): VenueDetails? = withContext(Dispatchers.IO) {
        try {
            apiService.getVenueDetails(venueId)?.data?.toDomainModel()
        } catch (e: Exception) {
            // Log the error if needed
            null
        }
    }
}