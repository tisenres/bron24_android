//package com.bron24.bron24_android.screens.cityselection.data
//
//import android.content.Context
//import com.bron24.bron24_android.screens.cityselection.domain.entities.City
//import com.bron24.bron24_android.screens.cityselection.domain.repository.CityRepository
//import javax.inject.Inject
//
//class CityRepositoryImpl @Inject constructor(
//    context: Context
//) : CityRepository {
//    private val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
//
//    override fun getAvailableCities(): List<City> {
//        return City.entries
//    }
//
//    override fun setSelectedCity(city: City) {
//        sharedPreferences.edit().putString("selected_city", city.name).apply()
//    }
//}