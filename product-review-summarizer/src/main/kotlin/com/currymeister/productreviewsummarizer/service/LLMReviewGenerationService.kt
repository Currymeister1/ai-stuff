package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.entity.ProductReview
import com.currymeister.productreviewsummarizer.dto.GeneratedReviewDto
import com.currymeister.productreviewsummarizer.dto.ReviewSpecificationDto
import com.currymeister.productreviewsummarizer.repository.ProductReviewRepository
import com.currymeister.productreviewsummarizer.service.ai.ReviewGeneratorAssistant
import org.springframework.stereotype.Service

@Service
class LLMReviewGenerationService(
    private val reviewGeneratorAssistant: ReviewGeneratorAssistant,
    private val productReviewRepository: ProductReviewRepository,
) {

    companion object {
        private const val REVIEWS_PER_BATCH = 10
    }

    fun generateReviews() {
        val reviews = productReviewRepository.findAllMissingGeneratedText()
        if (reviews.isEmpty()) return

        val results = reviews
            .groupBy { it.product?.id }
            .values
            .flatMap { productReviews -> generateForProduct(productReviews) }

        val reviewsById = reviews.associateBy { it.id }
        val updatedReviews = results.mapNotNull { result ->
            reviewsById[result.reviewId]?.apply {
                title = result.reviewTitle
                text = result.reviewText
            }
        }

        productReviewRepository.saveAll(updatedReviews)
    }

    private fun generateForProduct(reviews: List<ProductReview>): List<GeneratedReviewDto> {
        val productName = reviews.first().product?.productName ?: return emptyList()

        return reviews
            .chunked(REVIEWS_PER_BATCH)
            .flatMap { batch ->
                reviewGeneratorAssistant
                    .generateReviews(productName, batch.map { it.toReviewSpecificationDto() })
                    .reviews
            }
    }
}

private fun ProductReview.toReviewSpecificationDto(): ReviewSpecificationDto = ReviewSpecificationDto(
    reviewId = requireNotNull(id),
    reviewerAge = reviewerAge ?: 0,
    reviewStars = stars.toIntOrNull() ?: 0,
    requiredTopics = requiredTopics.sortedBy { it.id }.map { it.topic },
)
