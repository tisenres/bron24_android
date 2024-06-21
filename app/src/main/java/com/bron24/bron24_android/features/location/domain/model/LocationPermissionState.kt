package com.bron24.bron24_android.features.location.domain.model

sealed class LocationPermissionState {
    data object Granted : LocationPermissionState()
    data object Denied : LocationPermissionState()
}