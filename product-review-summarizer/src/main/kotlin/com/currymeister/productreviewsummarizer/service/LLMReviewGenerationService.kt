package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.util.FileUtil
import org.springframework.stereotype.Service

@Service
class LLMReviewGenerationService(private val reviewGeneratorService: ReviewGeneratorService) {

    fun generateReviews() {
        val reviewFileEntries = FileUtil.loadReviewsFile()

        for (reviewFileEntry in reviewFileEntries.reviews) {

        }
    }

}