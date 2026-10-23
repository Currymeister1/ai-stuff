package com.currymeister.productreviewsummarizer.controller

import com.currymeister.productreviewsummarizer.modal.ReviewFileEntries
import com.currymeister.productreviewsummarizer.service.ProductReviewJsonGeneratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-review")
class ProductReviewController(
    val productReviewJsonGeneratorService: ProductReviewJsonGeneratorService
) {

    @PostMapping("/generate-mock-reviews")
    fun generateReviews(): String {
        productReviewJsonGeneratorService.createReviewJsonFile()

        return "Mock reviews created"
    }

    @GetMapping("/mock-reviews")
    fun getMockReviews(): ReviewFileEntries =
        productReviewJsonGeneratorService.loadReviews()
}
