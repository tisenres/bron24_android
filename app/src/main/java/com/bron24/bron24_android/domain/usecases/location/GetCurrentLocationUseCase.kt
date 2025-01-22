package com.bron24.bron24_android.domain.usecases.location

import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    fun execute(): Flow<Location> = locationRepository.getCurrentLocation()
}