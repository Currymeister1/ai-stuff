package com.currymeister.productreviewsummarizer.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Input sent to the LLM. [toString] is interpolated into the prompt by
 * LangChain4j, so these property names must serialize to the snake_case keys
 * documented in prompt.md.
 */
@Serializable
data class ReviewSpecificationDto(
    @SerialName("review_id") val reviewId: Int,
    @SerialName("reviewer_age") val reviewerAge: Int,
    @SerialName("review_stars") val reviewStars: Int,
    @SerialName("required_topics") val requiredTopics: List<String>,
) {
    override fun toString() = Json.encodeToString(this)
}

/**
 * Output parsed by LangChain4j. LangChain4j uses Jackson and derives the JSON
 * schema from the Kotlin property names, so the LLM contract is camelCase
 * (see the output contract in prompt.md). Do not add kotlinx annotations here:
 * they are ignored by LangChain4j and would only suggest a contract that is
 * not actually enforced.
 */
data class GeneratedReviewDto(
    val reviewId: Int,
    val reviewTitle: String,
    val reviewText: String,
)

data class GeneratedReviewsDto(
    val reviews: List<GeneratedReviewDto>,
)
