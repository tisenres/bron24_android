package com.bron24.bron24_android.features.venuelisting.data.dto

import com.google.gson.annotations.SerializedName

data class VenueDto(
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("venue_type") val venueType: String,
    @SerializedName("venue_surface") val venueSurface: String,
    @SerializedName("people_capacity") val peopleCapacity: Int,
    @SerializedName("sport_type") val sportType: String,
    @SerializedName("price_per_hour") val pricePerHour: String,
    @SerializedName("description") val description: String,
    @SerializedName("working_hours_from") val workingHoursFrom: String,
    @SerializedName("working_hours_till") val workingHoursTill: String,
    @SerializedName("contact_1") val contact1: String,
    @SerializedName("contact_2") val contact2: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("preview_image") val previewImage: String,
    @SerializedName("address") val address: AddressDto,
    @SerializedName("city") val city: CityDto,
    @SerializedName("infrastructure") val infrastructure: InfrastructureDto,
    @SerializedName("venue_owner") val venueOwner: VenueOwnerDto
)

data class AddressDto(
    @SerializedName("id") val id: Int,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("district") val district: String,
    @SerializedName("closest_metro_station") val closestMetroStation: String
)

data class CityDto(
    @SerializedName("id") val id: Int,
    @SerializedName("city_name") val cityName: String
)

data class InfrastructureDto(
    @SerializedName("id") val id: Int,
    @SerializedName("locker_room") val lockerRoom: Boolean,
    @SerializedName("stands") val stands: String,
    @SerializedName("shower") val shower: Boolean,
    @SerializedName("parking") val parking: Boolean
)

data class VenueOwnerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("tin_number") val tinNumber: Int,
    @SerializedName("contact_1") val contact1: String,
    @SerializedName("contact_2") val contact2: String
)
