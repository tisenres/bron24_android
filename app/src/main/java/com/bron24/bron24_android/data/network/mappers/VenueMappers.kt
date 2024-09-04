package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.venue.AddressDto
import com.bron24.bron24_android.data.network.dto.venue.CityDto
import com.bron24.bron24_android.data.network.dto.venue.InfrastructureDto
import com.bron24.bron24_android.data.network.dto.venue.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDetailsDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDto
import com.bron24.bron24_android.data.network.dto.venue.VenueOwnerDto
import com.bron24.bron24_android.domain.entity.venue.Address
import com.bron24.bron24_android.domain.entity.venue.City
import com.bron24.bron24_android.domain.entity.venue.Infrastructure
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.entity.venue.VenueOwner

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId ?: 0,
        venueName = venueName.orEmpty(),
        pricePerHour = formatPrice(pricePerHour) ?: "0",
        address = address.toDomainModel() ?: Address(0, "", "", ""),
        imageUrl = null
    )
}

fun AddressDto.toDomainModel(): Address {
    return Address(
        id = id ?: 0,
        addressName = addressName.orEmpty(),
        district = district.orEmpty(),
        closestMetroStation = closestMetroStation.orEmpty()
    )
}

fun CityDto.toDomainModel(): City {
    return City(
        id = id ?: 0,
        cityName = cityName.orEmpty()
    )
}

fun InfrastructureDto.toDomainModel(): Infrastructure {
    return Infrastructure(
        id = id ?: 0,
        lockerRoom = lockerRoom ?: false,
        stands = stands ?: "",
        shower = shower ?: false,
        parking = parking ?: false
    )
}

fun VenueCoordinatesDto.toDomainModel(): VenueCoordinates {
    return VenueCoordinates(
        venueId = venueId ?: 0,
        venueName = venueName.orEmpty(),
        latitude = latitude?.toString() ?: "0.0",
        longitude = longitude?.toString() ?: "0.0"
    )
}

fun VenueOwnerDto.toDomainModel(): VenueOwner {
    return VenueOwner(
        id = id ?: 0,
        ownerName = ownerName.orEmpty(),
        tinNumber = tinNumber ?: 0,
        contact1 = contact1.orEmpty(),
        contact2 = contact2.orEmpty()
    )
}

fun VenueDetailsDto.toDomainModel(): VenueDetails {
    return VenueDetails(
        venueId = venueId ?: 0,
        address = address.toDomainModel() ?: Address(0, "", "", ""),
        city = city.toDomainModel() ?: City(0, ""),
        infrastructure = infrastructure.toDomainModel() ?: Infrastructure(0, false, "", false, false),
        venueOwner = venueOwner?.toDomainModel() ?: VenueOwner(0, "", 0, "", ""),
        venueName = venueName.orEmpty(),
        venueType = venueType.orEmpty(),
        venueSurface = venueSurface.orEmpty(),
        peopleCapacity = peopleCapacity ?: 0,
        sportType = sportType.orEmpty(),
        pricePerHour = formatPrice(pricePerHour) ?: "0",
        description = description.orEmpty(),
        workingHoursFrom = workingHoursFrom.orEmpty(),
        workingHoursTill = workingHoursTill.orEmpty(),
        contact1 = contact1.orEmpty(),
        contact2 = contact2.orEmpty(),
        createdAt = createdAt.orEmpty(),
        updatedAt = updatedAt.orEmpty(),
        imageUrls = emptyList()
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