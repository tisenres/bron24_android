package com.bron24.bron24_android.features.venuelisting.data.mappers

import com.bron24.bron24_android.features.venuelisting.data.dto.AddressDto
import com.bron24.bron24_android.features.venuelisting.data.dto.CityDto
import com.bron24.bron24_android.features.venuelisting.data.dto.InfrastructureDto
import com.bron24.bron24_android.features.venuelisting.data.dto.VenueDto
import com.bron24.bron24_android.features.venuelisting.data.dto.VenueOwnerDto
import com.bron24.bron24_android.features.venuelisting.domain.entities.Address
import com.bron24.bron24_android.features.venuelisting.domain.entities.City
import com.bron24.bron24_android.features.venuelisting.domain.entities.Infrastructure
import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue
import com.bron24.bron24_android.features.venuelisting.domain.entities.VenueOwner

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId,
        venueName = venueName,
        venueType = venueType,
        venueSurface = venueSurface,
        peopleCapacity = peopleCapacity,
        sportType = sportType,
        pricePerHour = pricePerHour,
        description = description,
        workingHoursFrom = workingHoursFrom,
        workingHoursTill = workingHoursTill,
        contact1 = contact1,
        contact2 = contact2,
        createdAt = createdAt,
        updatedAt = updatedAt,
        previewImage = previewImage.replace("127.0.0.1", "10.0.2.2"),
//        previewImage = previewImage,
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
        longitude = longitude,
        latitude = latitude,
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
