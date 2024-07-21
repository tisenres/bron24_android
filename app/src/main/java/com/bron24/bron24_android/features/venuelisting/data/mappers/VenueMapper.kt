package com.bron24.bron24_android.features.venuelisting.data.mappers

import com.bron24.bron24_android.features.venuelisting.data.dto.VenueDto
import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue

fun VenueDto.toDomainModel(): Venue {
    return Venue(
        venueId = venueId,
        name = venueName,
        type = venueType,
        surface = venueSurface,
        capacity = peopleCapacity,
        sportType = sportType,
        price = pricePerHour,
        description = description,
        workingHoursFrom = workingHoursFrom,
        workingHoursTill = workingHoursTill,
        contact1 = contact1,
        contact2 = contact2,
        city = city,
        infrastructure = infrastructure,
        address = address,
        owner = venueOwner
    )
}
