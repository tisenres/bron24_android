package com.bron24.bron24_android.domain.usecases.review

import com.bron24.bron24_android.domain.entity.user.Review
import com.bron24.bron24_android.domain.repository.ReviewRepository

class GetVenueReviewsUseCase(private val reviewRepository: ReviewRepository) {

    fun execute(venueId: String): List<Review> {
        return reviewRepository.getVenueReviews(venueId)
    }
}