package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.config.AppConfig
import org.springframework.stereotype.Service

@Service
class LLMReviewGenerationService(private val reviewGeneratorService: ReviewGeneratorService) {
    
    fun generateReviews() {
        val reviewFileEntries = AppConfig.loadReviewsFile()

        for (reviewFileEntry in reviewFileEntries.reviews) {

        }
    }

}