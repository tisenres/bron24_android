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
import com.bron24.bron24_android.helper.extension.formatPrice

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId,
        venueName = venueName,
        pricePerHour = pricePerHour.formatPrice(),
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
        infrastructureName = infrastructureName,
        infrastructureDescription = infrastructureDescription ?: "",
        infrastructurePicture = infrastructurePicture ?: ""

//        lockerRoom = lockerRoom,
//        stands = stands,
//        shower = shower,
//        parking = parking
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
        venueId = venueId ?: 0,
        address = address?.toDomainModel() ?: Address(0, "", "", ""),
        city = city?.toDomainModel() ?: City(0, ""),
        infrastructure = infrastructure?.map { it.toDomainModel() } ?: emptyList(),
        venueOwner = venueOwner?.toDomainModel() ?: VenueOwner(0, "", 0, "", ""),
        venueName = venueName ?: "",
        venueType = venueType ?: "",
        venueSurface = venueSurface ?: "",
        peopleCapacity = peopleCapacity ?: 0,
        sportType = sportType ?: "",
        pricePerHour = pricePerHour?.formatPrice() ?: "0",
        description = description ?: "",
        workingHoursFrom = workingHoursFrom ?: "",
        workingHoursTill = workingHoursTill ?: "",
        contact1 = contact1 ?: "",
        contact2 = contact2 ?: "",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: "",
        imageUrls = emptyList(),
        longitude = longitude?.toDoubleOrNull() ?: 0.0,
        latitude = latitude?.toDoubleOrNull() ?: 0.0,
        distance = distance ?: 0.0,
        sectors = sectors ?: emptyList()
    )
}

//fun formatPrice(price: String): String {
//    return try {
//        val floatPrice = price.toFloat()
//        val intPrice = floatPrice.toInt()
//        String.format("%,d", intPrice).replace(",", " ")
//    } catch (e: NumberFormatException) {
//        "0" // Return "0" if parsing fails
//    }
//}