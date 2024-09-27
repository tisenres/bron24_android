package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class VenueDetailsResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: VenueDetailsDto
)

data class VenueDetailsDto(
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("address") val address: AddressDto,
    @SerializedName("city") val city: CityDto,
    @SerializedName("infrastructure") val infrastructure: InfrastructureDto,
    @SerializedName("venue_owner") val venueOwner: VenueOwnerDto?,
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
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("sector_number") val sectorNumber: Int,
    @SerializedName("distance") val distance: Double,
    @SerializedName("sectors") val sectors: List<String>
)