package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.AddressDto
import com.bron24.bron24_android.data.network.dto.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.VenueDto
import com.bron24.bron24_android.domain.entity.venue.Address
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueName = venueName,
        pricePerHour = formatPrice(pricePerHour),
        address = address.toDomainModel()
    )
}

fun AddressDto.toDomainModel(): Address {
    return Address(
        id = id,
        addressName = addressName,
        district = district,
        closestMetroStation = closestMetroStation
    )
}

fun VenueCoordinatesDto.toDomainModel(): VenueCoordinates {
    return VenueCoordinates(
        venueName = venueName,
        latitude = latitude,
        longitude = longitude
    )
}

fun formatPrice(price: String): String {
    val floatPrice = price.toFloat()
    val intPrice = floatPrice.toInt()
    return String.format("%,d", intPrice).replace(",", " ")
}