package com.currymeister.productreviewsummarizer.dto

data class ReviewSummaryResponse(
    val reviewId: Int,
    val reviewerAge: Int,
    val reviewStars: Int,
    val requiredTopics: List<String>,
    val reviewTitle: String? = null,
)

data class ProductReviewsResponse(
    val productId: Int,
    val productName: String,
    val reviews: List<ReviewSummaryResponse>,
)

data class ReviewOverviewResponse(
    val products: List<ProductReviewsResponse>,
)

data class ReviewDetailResponse(
    val productId: Int,
    val productName: String,
    val reviewId: Int,
    val reviewerAge: Int,
    val reviewStars: Int,
    val requiredTopics: List<String>,
    val reviewTitle: String? = null,
    val reviewText: String? = null,
)
