package com.bron24.bron24_android.features.cityselection.domain.repository

import com.bron24.bron24_android.features.cityselection.domain.entities.City

interface CityRepository {
    fun getAvailableCities(): List<City>
    fun setSelectedCity(city: City)
}
