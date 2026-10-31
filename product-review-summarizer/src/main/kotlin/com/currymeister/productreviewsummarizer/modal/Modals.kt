package com.currymeister.productreviewsummarizer.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ReviewEntryDto(
    @SerialName("review_id") val reviewId: Int,
    @SerialName("reviewer_age") val reviewerAge: Int,
    @SerialName("review_stars") val reviewStars: Int,
    @SerialName("required_topics") val requiredTopics: List<String>,
) {
    override fun toString() = Json.encodeToString(this)
}

@Serializable
data class ReviewResult (
    @SerialName("review_id") val reviewId: Int,
    @SerialName("review_title") val reviewTitle: String,
    @SerialName("review_text") val reviewText: String,
)

@Serializable
data class ReviewResults(
    @SerialName("reviews") val reviews: List<ReviewResult>,
)

@Serializable
data class ReviewEntryResponse(
    @SerialName("review_id") val reviewId: Int,
    @SerialName("reviewer_age") val reviewerAge: Int,
    @SerialName("review_stars") val reviewStars: Int,
    @SerialName("required_topics") val requiredTopics: List<String>,
    @SerialName("generated_review_title") val generatedReviewTitle: String? = null,
)

@Serializable
data class ReviewsFileEntryResponse(
    @SerialName("product_id") val productId: Int,
    @SerialName("product_name") val productName: String,
    @SerialName("reviews") val reviews: List<ReviewEntryResponse>,
)

@Serializable
data class ReviewFileEntriesResponse(
    @SerialName("reviews") val reviews: List<ReviewsFileEntryResponse>,
)

@Serializable
data class ReviewDetailResponse(
    @SerialName("product_id") val productId: Int,
    @SerialName("product_name") val productName: String,
    @SerialName("review_id") val reviewId: Int,
    @SerialName("reviewer_age") val reviewerAge: Int,
    @SerialName("review_stars") val reviewStars: Int,
    @SerialName("required_topics") val requiredTopics: List<String>,
    @SerialName("review_title") val reviewTitle: String? = null,
    @SerialName("review_text") val reviewText: String? = null,
)
