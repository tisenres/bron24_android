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
    @SerializedName("infrastructure") val infrastructure: List<InfrastructureDto>,
    @SerializedName("venue_owner") val venueOwner: VenueOwnerDto?,
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("venue_type_display") val venueType: String,
    @SerializedName("venue_surface_display") val venueSurface: String,
    @SerializedName("sport_type_display") val sportType: String,
    @SerializedName("people_capacity") val peopleCapacity: Int,
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
    @SerializedName("available_time_slots") val slots: Int,
    @SerializedName("preview_image") val previewImg: String,
    @SerializedName("play_whole_stadium") val playWhole: Boolean,
    @SerializedName("distance") val distance: Double,
    @SerializedName("share_link") val shareLink: String,
    @SerializedName("sectors") val sectors: List<String>
)