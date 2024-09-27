package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.venue.AddressDto
import com.bron24.bron24_android.data.network.dto.venue.CityDto
import com.bron24.bron24_android.data.network.dto.venue.InfrastructureDto
import com.bron24.bron24_android.data.network.dto.venue.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDetailsDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDto
import com.bron24.bron24_android.data.network.dto.venue.VenueOwnerDto
import com.bron24.bron24_android.data.network.dto.venue.VenueResponseDto
import com.bron24.bron24_android.domain.entity.venue.Address
import com.bron24.bron24_android.domain.entity.venue.City
import com.bron24.bron24_android.domain.entity.venue.Infrastructure
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.entity.venue.VenueOwner

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId,
        venueName = venueName,
        pricePerHour = formatPrice(pricePerHour),
        address = address.toDomainModel(),
        distance = distance,
        previewImage = previewImage
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

fun CityDto.toDomainModel(): City {
    return City(
        id = id,
        cityName = cityName
    )
}

fun InfrastructureDto.toDomainModel(): Infrastructure {
    return Infrastructure(
        id = id,
        lockerRoom = lockerRoom,
        stands = stands,
        shower = shower,
        parking = parking
    )
}

fun VenueCoordinatesDto.toDomainModel(): VenueCoordinates {
    return VenueCoordinates(
        venueId = venueId,
        venueName = venueName,
        latitude = latitude.toString(),
        longitude = longitude.toString()
    )
}

fun VenueOwnerDto.toDomainModel(): VenueOwner {
    return VenueOwner(
        id = id,
        ownerName = ownerName,
        tinNumber = tinNumber,
        contact1 = contact1,
        contact2 = contact2
    )
}

fun VenueDetailsDto.toDomainModel(): VenueDetails {
    return VenueDetails(
        venueId = venueId,
        address = address.toDomainModel(),
        city = city.toDomainModel(),
        infrastructure = infrastructure.toDomainModel(),
        venueOwner = venueOwner?.toDomainModel() ?: VenueOwner(0, "", 0, "", ""),
        venueName = venueName,
        venueType = venueType,
        venueSurface = venueSurface,
        peopleCapacity = peopleCapacity,
        sportType = sportType,
        pricePerHour = formatPrice(pricePerHour),
        description = description,
        workingHoursFrom = workingHoursFrom,
        workingHoursTill = workingHoursTill,
        contact1 = contact1,
        contact2 = contact2,
        createdAt = createdAt,
        updatedAt = updatedAt,
        imageUrls = emptyList(),
        longitude = longitude.toDouble(),
        latitude = latitude.toDouble(),
        distance = distance,
        sectors = sectors
    )
}

fun formatPrice(price: String): String {
    return try {
        val floatPrice = price.toFloat()
        val intPrice = floatPrice.toInt()
        String.format("%,d", intPrice).replace(",", " ")
    } catch (e: NumberFormatException) {
        "0" // Return "0" if parsing fails
    }
}