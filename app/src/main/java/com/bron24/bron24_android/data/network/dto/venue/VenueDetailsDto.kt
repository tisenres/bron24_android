package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class VenueDetailsResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: VenueDetailsDto
)

data class VenueDetailsDto(
    @SerializedName("venue_id") val venueId: Int? = null,
    @SerializedName("address") val address: AddressDto? = null,
    @SerializedName("city") val city: CityDto? = null,
    @SerializedName("infrastructure") val infrastructure: List<InfrastructureDto>? = emptyList(),
    @SerializedName("venue_owner") val venueOwner: VenueOwnerDto? = null,
    @SerializedName("venue_name") val venueName: String? = null,
    @SerializedName("venue_type") val venueType: String? = null,
    @SerializedName("venue_surface") val venueSurface: String? = null,
    @SerializedName("people_capacity") val peopleCapacity: Int? = null,
    @SerializedName("sport_type") val sportType: String? = null,
    @SerializedName("price_per_hour") val pricePerHour: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("working_hours_from") val workingHoursFrom: String? = null,
    @SerializedName("working_hours_till") val workingHoursTill: String? = null,
    @SerializedName("contact_1") val contact1: String? = null,
    @SerializedName("contact_2") val contact2: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String? = null,
    @SerializedName("sector_number") val sectorNumber: Int? = null,
    @SerializedName("distance") val distance: Double? = null,
    @SerializedName("sectors") val sectors: List<String>? = emptyList()
)