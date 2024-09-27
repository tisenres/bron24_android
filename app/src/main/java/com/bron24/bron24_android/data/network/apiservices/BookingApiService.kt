package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesRequestDto
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface BookingApiService {
    @POST("api/v1/booking/available/")
    suspend fun getAvailableTimes(@Body request: AvailableTimesRequestDto): AvailableTimesResponseDto
}
