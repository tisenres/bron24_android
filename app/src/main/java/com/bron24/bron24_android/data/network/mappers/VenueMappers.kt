package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.venue.AddressDto
import com.bron24.bron24_android.data.network.dto.venue.CityDto
import com.bron24.bron24_android.data.network.dto.venue.InfrastructureDto
import com.bron24.bron24_android.data.network.dto.venue.SpecialOfferDto
import com.bron24.bron24_android.data.network.dto.venue.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDetailsDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDto
import com.bron24.bron24_android.data.network.dto.venue.VenueOwnerDto
import com.bron24.bron24_android.domain.entity.offers.SpecialOffer
import com.bron24.bron24_android.domain.entity.venue.Address
import com.bron24.bron24_android.domain.entity.venue.City
import com.bron24.bron24_android.domain.entity.venue.Infrastructure
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.entity.venue.VenueOwner
import com.bron24.bron24_android.helper.util.formatPrice

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId,
        venueName = venueName,
        pricePerHour = pricePerHour.formatPrice(),
        address = address.toDomainModel(),
        rate = rate,
        slots = slots,
        distance = distance,
        previewImage = previewImage,
        favourite = false
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
        name = name,
        staticName = staticName,
        description = description,
        picture = picture
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
        infrastructure = infrastructure.map { it.toDomainModel() },
        venueOwner = venueOwner?.toDomainModel() ?: VenueOwner(0, "", 0, "", ""),
        venueType = venueType,
        venueName = venueName,
        description = description,
        venueSurface = venueSurface,
        peopleCapacity = peopleCapacity,
        sportType = sportType,
        pricePerHour = pricePerHour.formatPrice(),
        workingHoursFrom = workingHoursFrom,
        workingHoursTill = workingHoursTill,
        contact1 = contact1,
        contact2 = contact2,
        createdAt = createdAt,
        updatedAt = updatedAt,
        imageUrl = previewImg,
        longitude = longitude.toDouble(),
        latitude = latitude.toDouble(),
        distance = distance,
        sectors = sectors
    )
}

fun SpecialOfferDto.toDomainModel(): SpecialOffer {
    return SpecialOffer(
        id = id ?: 0,
        imageUrl = image ?: "",
        createdAt = createdAt ?: "",
        validUntil = validUntil ?: ""
    )
}