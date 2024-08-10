package com.bron24.bron24_android.domain.entity.user

import com.bron24.bron24_android.domain.entity.venue.Venue
import java.time.LocalDateTime

data class Review(
    val reviewId: String,
    val user: User,
    val venue: Venue,
    val rating: Int,
    val comment: String,
    val reviewDate: LocalDateTime
)