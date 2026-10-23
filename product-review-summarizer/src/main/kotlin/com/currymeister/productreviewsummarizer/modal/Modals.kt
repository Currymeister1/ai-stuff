package com.currymeister.productreviewsummarizer.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.random.Random

@Serializable
data class ReviewsFileEntry(
    @SerialName("product_id") val productId: Int,
    @SerialName("product_name") val productName: String,
    @SerialName("reviews") val reviews: List<ReviewEntryDto>
)

@Serializable
data class ReviewFileEntries(
    val reviews: List<ReviewsFileEntry>
) {
    override fun toString() = Json.encodeToString(this)
}

@Serializable
data class ProductsConfigFile(
    @SerialName("product_id") val productId: Int,
    @SerialName("product_name") val productName: String,
    @SerialName("category") val category: String,
    @SerialName("age") private val age: ProductConfigAge,
    @SerialName("review_id_range") private val reviewIdRange: ProductConfigReviewIdRange,
    @SerialName("general_review_stars") private val generalReviewStars: Int,
) {
    companion object {
        private val allStars = listOf(1, 2, 3, 4, 5)
        private val othersByStar = allStars.associateWith { s -> allStars - s }
    }

    fun getRandomAge(): Int = IntRange(age.min, age.max).random()

    fun getReviewIdRange(): IntRange = reviewIdRange.start..reviewIdRange.end

    fun getRandomReviewStar(matchChance: Double = 0.7): Int =
        if (Random.nextDouble() < matchChance) generalReviewStars else othersByStar.getValue(generalReviewStars)
            .random()
}

@Serializable
data class ProductConfigAge(
    @SerialName("min") val min: Int,
    @SerialName("max") val max: Int,
)

@Serializable
data class ProductConfigReviewIdRange(
    @SerialName("start") val start: Int,
    @SerialName("end") val end: Int,
)

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