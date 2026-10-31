package com.currymeister.productreviewsummarizer.controller.admin

import com.currymeister.productreviewsummarizer.dto.ReviewDetailResponse
import com.currymeister.productreviewsummarizer.dto.ReviewOverviewResponse
import com.currymeister.productreviewsummarizer.service.LLMReviewGenerationService
import com.currymeister.productreviewsummarizer.service.ProductReviewEmbeddingService
import com.currymeister.productreviewsummarizer.service.ProductReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin/product-review")
class ProductReviewAdminController(
    private val productReviewService: ProductReviewService,
    private val llmReviewGenerationService: LLMReviewGenerationService,
    private val productReviewEmbeddingService: ProductReviewEmbeddingService,
) {

    @PostMapping("/generate-mock-reviews")
    fun generateReviews(): String {
        productReviewService.generateMockReviews()

        return "Mock reviews created"
    }

    @GetMapping("/mock-reviews")
    fun getMockReviews(): ReviewOverviewResponse =
        productReviewService.loadReviewsWithGeneratedTitles()

    @GetMapping("/mock-reviews/{reviewId}")
    fun getMockReviewDetail(
        @PathVariable reviewId: Int
    ): ResponseEntity<ReviewDetailResponse> =
        productReviewService.loadReviewDetail(reviewId)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/generate-text-for-reviews")
    fun generateTextForReviews(): String {
        llmReviewGenerationService.generateReviews()

        return "Reviews were generated successfully!"
    }

    @PostMapping("/embed-reviews")
    fun embedReviews(): String {
        productReviewEmbeddingService.embedProductReviews()

        return "Reviews were embedded successfully!"
    }
}
