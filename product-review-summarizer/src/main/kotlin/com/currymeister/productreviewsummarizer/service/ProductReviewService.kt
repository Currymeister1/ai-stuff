package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.entity.ProductReview
import com.currymeister.productreviewsummarizer.entity.RequiredTopic
import com.currymeister.productreviewsummarizer.dto.ProductReviewsResponse
import com.currymeister.productreviewsummarizer.dto.ReviewDetailResponse
import com.currymeister.productreviewsummarizer.dto.ReviewOverviewResponse
import com.currymeister.productreviewsummarizer.dto.ReviewSummaryResponse
import com.currymeister.productreviewsummarizer.repository.ProductRepository
import com.currymeister.productreviewsummarizer.repository.ProductReviewConfigRepository
import com.currymeister.productreviewsummarizer.repository.ProductReviewRepository
import com.currymeister.productreviewsummarizer.repository.RequiredTopicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductReviewService(
    private val productRepository: ProductRepository,
    private val productReviewRepository: ProductReviewRepository,
    private val productReviewConfigRepository: ProductReviewConfigRepository,
    private val requiredTopicRepository: RequiredTopicRepository,
) {

    @Transactional
    fun generateMockReviews() {
        val products = productRepository.findAllByOrderByIdAsc()
        val configsByProductId = productReviewConfigRepository.findAllWithProduct()
            .associateBy { it.product?.id }
        val topics = requiredTopicRepository.findAll()

        products.forEach { product ->
            val config = configsByProductId[product.id] ?: return@forEach

            repeat(config.reviewCount) {
                productReviewRepository.save(
                    ProductReview(
                        product = product,
                        stars = config.randomStar().toString(),
                        reviewerAge = config.randomAge(),
                        requiredTopics = selectRequiredTopics(topics),
                    )
                )
            }
        }
    }

    @Transactional(readOnly = true)
    fun loadReviewsWithGeneratedTitles(): ReviewOverviewResponse {
        val reviewsByProductId = productReviewRepository.findAllWithProductAndTopics()
            .groupBy { it.product?.id }

        return ReviewOverviewResponse(
            products = productRepository.findAllByOrderByIdAsc().map { product ->
                ProductReviewsResponse(
                    productId = requireNotNull(product.id),
                    productName = product.productName,
                    reviews = reviewsByProductId[product.id].orEmpty().map { it.toReviewSummaryResponse() },
                )
            },
        )
    }

    @Transactional(readOnly = true)
    fun loadReviewDetail(reviewId: Int): ReviewDetailResponse? {
        val review = productReviewRepository.findWithProductAndTopicsById(reviewId) ?: return null
        val product = review.product ?: return null

        return ReviewDetailResponse(
            productId = requireNotNull(product.id),
            productName = product.productName,
            reviewId = requireNotNull(review.id),
            reviewerAge = review.reviewerAge ?: 0,
            reviewStars = review.stars.toIntOrNull() ?: 0,
            requiredTopics = review.requiredTopicNames(),
            reviewTitle = review.title,
            reviewText = review.text,
        )
    }

    private fun selectRequiredTopics(topics: List<RequiredTopic>): MutableSet<RequiredTopic> {
        if (topics.isEmpty()) return mutableSetOf()

        val count = IntRange(1, topics.size).random()
        return topics.shuffled().take(count).toMutableSet()
    }
}

private fun ProductReview.toReviewSummaryResponse(): ReviewSummaryResponse = ReviewSummaryResponse(
    reviewId = requireNotNull(id),
    reviewerAge = reviewerAge ?: 0,
    reviewStars = stars.toIntOrNull() ?: 0,
    requiredTopics = requiredTopicNames(),
    reviewTitle = title,
)

private fun ProductReview.requiredTopicNames(): List<String> =
    requiredTopics.sortedBy { it.id }.map { it.topic }
