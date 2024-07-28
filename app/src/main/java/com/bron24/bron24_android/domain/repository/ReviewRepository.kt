package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.Review

interface ReviewRepository {
    fun addReview(review: Review): Review
    fun getVenueReviews(venueId: String): List<Review>
}
