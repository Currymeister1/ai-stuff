package com.currymeister.productreviewsummarizer.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-review-summarizer")
class ProductReviewSummarizerController {

    @GetMapping
    fun getReviewSummary(@RequestParam("q") q: String) : Map<String, String> = mapOf("lower" to q.lowercase(), "upper" to q.uppercase())
}