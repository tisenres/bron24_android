//package com.bron24.bron24_android.location.data.repository
//
//import com.bron24.bron24_android.data.PermissionChecker
//import com.bron24.bron24_android.features.location.domain.entities.LocationPermissionState
//import com.bron24.bron24_android.features.location.domain.repository.LocationRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import javax.inject.Inject
//
//class LocationRepositoryImpl @Inject constructor(
//    private val permissionChecker: PermissionChecker
//) : LocationRepository {
//    override fun checkLocationPermission(): Flow<LocationPermissionState> = flow {
//        val permissionState = if (permissionChecker.hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//            LocationPermissionState.Granted
//        } else {
//            LocationPermissionState.Denied
//        }
//        emit(permissionState)
//    }
//}
