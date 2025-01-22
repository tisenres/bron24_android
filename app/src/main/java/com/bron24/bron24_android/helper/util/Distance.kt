package com.bron24.bron24_android.helper.util

import com.bron24.bron24_android.domain.entity.user.Location
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun calculateDistance(
    userLocation: Location, // Sizning joylashuvingiz
    venueLocation: Location  // Berilgan joylashuv
): Double {
    val earthRadius = 6371.0 // Yer radiusi (kilometrda)

    // Radyanga aylantirish
    val dLat = Math.toRadians(venueLocation.latitude - userLocation.latitude)
    val dLon = Math.toRadians(venueLocation.longitude - userLocation.longitude)

    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(userLocation.latitude)) * cos(Math.toRadians(venueLocation.longitude)) * sin(dLon / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c // Masofa (kilometrda)
}