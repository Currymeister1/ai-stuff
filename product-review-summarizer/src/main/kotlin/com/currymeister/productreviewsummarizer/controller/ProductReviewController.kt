package com.currymeister.productreviewsummarizer.controller

import com.currymeister.productreviewsummarizer.modal.ReviewDetailResponse
import com.currymeister.productreviewsummarizer.modal.ReviewFileEntriesResponse
import com.currymeister.productreviewsummarizer.service.LLMReviewGenerationService
import com.currymeister.productreviewsummarizer.service.ProductReviewJsonGeneratorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-review")
class ProductReviewController(
    val productReviewJsonGeneratorService: ProductReviewJsonGeneratorService,
    val llmReviewGenerationService: LLMReviewGenerationService
) {

    @PostMapping("/generate-mock-reviews")
    fun generateReviews(): String {
        productReviewJsonGeneratorService.createReviewJsonFile()

        return "Mock reviews created"
    }

    @GetMapping("/mock-reviews")
    fun getMockReviews(): ReviewFileEntriesResponse =
        productReviewJsonGeneratorService.loadReviewsWithGeneratedTitles()

    @GetMapping("/mock-reviews/{reviewId}")
    fun getMockReviewDetail(
        @PathVariable reviewId: Int
    ): ResponseEntity<ReviewDetailResponse> =
        productReviewJsonGeneratorService.loadReviewDetail(reviewId)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/generate-text-for-reviews")
    fun generateTextForReviews(): String {
        llmReviewGenerationService.generateReviews()

        return "Reviews were generated successfully!"
    }
}
