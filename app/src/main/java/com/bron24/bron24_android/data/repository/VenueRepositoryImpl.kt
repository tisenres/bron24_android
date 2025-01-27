package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.db.FavouriteDao
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.favourite.Favourite
import com.bron24.bron24_android.domain.entity.offers.SpecialOffer
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService,
    private val favouriteDao: FavouriteDao
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
            )?.data?.map {
                it.toDomainModel()
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> =
        withContext(Dispatchers.IO) {
            try {
                apiService.getVenuesCoordinates()?.map { it.toDomainModel() } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }

    override suspend fun getVenuePictures(venueId: Int): List<String> =
        withContext(Dispatchers.IO) {
            try {
                apiService.getVenuePictures(venueId)?.map { it.url } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }

    override fun getVenueDetailsById(
        venueId: Int,
        latitude: Double?,
        longitude: Double?
    ): Flow<VenueDetails> = flow{
        emit(apiService.getVenueDetails(venueId, latitude, longitude).data.toDomainModel())
    }

    override suspend fun searchVenues(
        query: String?,
        latitude: Double?,
        longitude: Double?,
    ): List<Venue> = withContext(Dispatchers.IO) {
        try {
            apiService.searchVenues(query, latitude, longitude)
                ?.data?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getAllFavourite(): Flow<List<Favourite>> = flow {
        try {
            favouriteDao.getAll().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun addFavourite(favourite: Favourite): Flow<Result<Unit>> = flow {
        try {
            favouriteDao.insert(favourite)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun deleteFavourite(id: Int): Flow<Result<Unit>> = flow {
        try {
            favouriteDao.delete(id)
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getSpecialOffers(): Flow<List<SpecialOffer>> = flow<List<SpecialOffer>> {
        emit(apiService.getSpecialOffers()?.data?.map { it.toDomainModel() } ?: emptyList())
    }
}