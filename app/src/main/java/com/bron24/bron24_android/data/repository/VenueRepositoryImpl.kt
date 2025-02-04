package com.bron24.bron24_android.data.repository

import android.util.Log
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.data.local.db.FavouriteDao
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.favourite.Favourite
import com.bron24.bron24_android.domain.entity.offers.SpecialOffer
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.helper.util.formatDateDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService, private val favouriteDao: FavouriteDao
) : VenueRepository {
    override suspend fun getVenues(
        latitude: Double?,
        longitude: Double?,
    ): List<Venue> = withContext(Dispatchers.IO) {
        try {
            apiService.getVenues(
                latitude, longitude,
            )?.data?.map {
                it.toDomainModel()
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> = withContext(Dispatchers.IO) {
        try {
            apiService.getVenuesCoordinates()?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getVenuePictures(venueId: Int): List<String> = withContext(Dispatchers.IO) {
        try {
            apiService.getVenuePictures(venueId)?.map { it.url } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getVenueDetailsById(
        venueId: Int, latitude: Double?, longitude: Double?
    ): Flow<VenueDetails> = flow {
        emit(apiService.getVenueDetails(venueId, latitude, longitude).data.toDomainModel())
    }

    override suspend fun searchVenues(
        query: String?,
        latitude: Double?,
        longitude: Double?,
    ): List<Venue> = withContext(Dispatchers.IO) {
        try {
            apiService.searchVenues(query, latitude, longitude)?.data?.map { it.toDomainModel() } ?: emptyList()
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

    override suspend fun getVenueByFilter(latitude: Double?, longitude: Double?, filterOptions: FilterOptions): List<Venue> =
        withContext(Dispatchers.IO) {
                val res = apiService.getVenueByFilter(
                    latitude = latitude,
                    longitude = longitude,
                    district = filterOptions.location.ifEmpty { null },
                    minPrice = filterOptions.minSumma,
                    maxPrice = filterOptions.maxSumma,
                    venueType = if (filterOptions.selOutdoor && filterOptions.selIndoor) "box" else if (filterOptions.selOutdoor) "out" else if (filterOptions.selIndoor) "in" else "",
                    room = filterOptions.selRoom,
                    shower = filterOptions.selShower,
                    parking = filterOptions.selParking,
                    date = filterOptions.selectedDate.formatDateDto().ifEmpty { null },
                    startTime = filterOptions.startTime,
                    endTime = filterOptions.endTime
                )
                res.data.map { it.toDomainModel() }
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

    override fun getSpecialOffers(): Flow<List<SpecialOffer>> = flow {
        try {
            emit(apiService.getSpecialOffers()?.data?.map { it.toDomainModel() } ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }

    }
}