package com.bron24.bron24_android.features.cityselection.domain.usecases

import com.bron24.bron24_android.features.cityselection.domain.model.City
import com.bron24.bron24_android.features.cityselection.domain.repository.CityRepository
import javax.inject.Inject

class UpdateSelectedCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    fun execute(city: City) {
        cityRepository.setSelectedCity(city)
    }
}