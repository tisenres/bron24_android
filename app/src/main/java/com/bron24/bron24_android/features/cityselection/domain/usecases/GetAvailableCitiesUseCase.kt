package com.bron24.bron24_android.features.cityselection.domain.usecases

import com.bron24.bron24_android.features.cityselection.domain.entities.City
import com.bron24.bron24_android.features.cityselection.domain.repository.CityRepository
import javax.inject.Inject

class GetAvailableCitiesUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    fun execute(): List<City> {
        return cityRepository.getAvailableCities()
    }
}