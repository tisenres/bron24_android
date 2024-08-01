package com.bron24.bron24_android.data.network

import com.bron24.bron24_android.data.network.dto.PictureDto
import com.bron24.bron24_android.data.network.dto.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.VenueDetailsDto
import com.bron24.bron24_android.data.network.dto.VenueDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VenueApiService {
    @GET("api/v1/venues/")
    suspend fun getVenues(): List<VenueDto>

    @GET("api/v1/venues/coordinates/")
    suspend fun getVenuesCoordinates(): List<VenueCoordinatesDto>

    @GET("api/v1/venues/pictures/{id}/")
    suspend fun getVenuePictures(@Path("id") id: Int): List<PictureDto>

    @GET("api/v1/venues/{id}/")
    suspend fun getVenueDetails(@Path("id") id: Int): VenueDetailsDto
}
