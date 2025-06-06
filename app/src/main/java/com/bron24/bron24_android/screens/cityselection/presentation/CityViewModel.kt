//package com.bron24.bron24_android.screens.cityselection.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.bron24.bron24_android.screens.cityselection.domain.entities.City
//import com.bron24.bron24_android.screens.cityselection.domain.usecases.GetAvailableCitiesUseCase
//import com.bron24.bron24_android.screens.cityselection.domain.usecases.UpdateSelectedCityUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class CityViewModel @Inject constructor(
//    private val getAvailableCitiesUseCase: GetAvailableCitiesUseCase,
//    private val updateSelectedCityUseCase: UpdateSelectedCityUseCase
//) : ViewModel() {
//
//    private val _selectedCity = MutableStateFlow<City?>(null)
//    val selectedCity: StateFlow<City?> = _selectedCity
//
//    private val _availableCities = MutableStateFlow<List<City>>(emptyList())
//    val availableCities: StateFlow<List<City>> = _availableCities
//
//    init {
//        viewModelScope.launch {
//            _availableCities.value = getAvailableCitiesUseCase.execute()
//        }
//    }
//
//    fun selectCity(city: City) {
//        _selectedCity.value = city
//    }
//
//    fun confirmCitySelection() {
//        viewModelScope.launch {
//            _selectedCity.value?.let { city ->
//                updateSelectedCityUseCase.execute(city)
//            }
//        }
//    }
//}