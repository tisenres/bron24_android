package com.bron24.bron24_android.data.network

import com.bron24.bron24_android.data.network.dto.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.VenueDto
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import retrofit2.http.GET

interface VenueApiService {
    @GET("api/v1/venues/")
    suspend fun getVenues(): List<VenueDto>

    @GET("api/v1/venues/coordinates/")
    suspend fun getVenuesCoordinates(): List<VenueCoordinatesDto>
}
