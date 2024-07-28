package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.AddressDto
import com.bron24.bron24_android.data.network.dto.CityDto
import com.bron24.bron24_android.data.network.dto.InfrastructureDto
import com.bron24.bron24_android.data.network.dto.VenueDto
import com.bron24.bron24_android.data.network.dto.VenueOwnerDto
import com.bron24.bron24_android.domain.entity.venue.Address
import com.bron24.bron24_android.domain.entity.venue.City
import com.bron24.bron24_android.domain.entity.venue.Infrastructure
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueOwner

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId,
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
        previewImage = previewImage.replace("127.0.0.1", "10.0.2.2"),
        address = address.toDomainModel(),
        city = city.toDomainModel(),
        infrastructure = infrastructure.toDomainModel(),
        venueOwner = venueOwner.toDomainModel()
    )
}

fun AddressDto.toDomainModel(): Address {
    return Address(
        id = id,
        addressName = addressName,
        longitude = longitude.toDouble(),
        latitude = latitude.toDouble(),
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

fun VenueOwnerDto.toDomainModel(): VenueOwner {
    return VenueOwner(
        id = id,
        ownerName = ownerName,
        tinNumber = tinNumber,
        contact1 = contact1,
        contact2 = contact2
    )
}

fun formatPrice(price: String): String {
    val floatPrice = price.toFloat()
    val intPrice = floatPrice.toInt()
    return String.format("%,d", intPrice).replace(",", " ")
}