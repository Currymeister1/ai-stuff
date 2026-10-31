package com.currymeister.productreviewsummarizer.controller

import com.currymeister.productreviewsummarizer.service.ProductReviewEmbeddingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-review-summarizer")
class ProductReviewSummarizerController (
    private val productReviewEmbeddingService: ProductReviewEmbeddingService
){

    @GetMapping
    fun getReviewSummary(@RequestParam("q") question: String) : Map<String, String>  = productReviewEmbeddingService.searchReviews(question)
}