package com.bron24.bron24_android.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.repository.LocationRepository
import com.bron24.bron24_android.helper.util.PermissionChecker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val permissionChecker: PermissionChecker
) : LocationRepository {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    override fun checkLocationPermission(): Flow<LocationPermissionState> = flow {
        if (permissionChecker.hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ||
            permissionChecker.hasPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            emit(LocationPermissionState.GRANTED)
        } else {
            emit(LocationPermissionState.DENIED)
        }
    }

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<Location> = callbackFlow {
        if (permissionChecker.hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ||
            permissionChecker.hasPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        trySend(Location(location.latitude, location.longitude)).isSuccess
                    }
                }
                .addOnFailureListener { e ->
                    close(e)
                }
        }
        awaitClose()
    }
}