package com.bron24.bron24_android.domain.usecases.location

import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.repository.LocationRepository

class GetCurrentLocationUseCase(private val locationRepository: LocationRepository) {

    fun execute(): Location {
        return locationRepository.getCurrentLocation()
    }
}