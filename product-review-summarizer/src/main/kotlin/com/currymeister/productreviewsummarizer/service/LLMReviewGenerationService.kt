package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.modal.ReviewResult
import com.currymeister.productreviewsummarizer.modal.ReviewResults
import com.currymeister.productreviewsummarizer.modal.ReviewsFileEntry
import com.currymeister.productreviewsummarizer.util.FileUtil
import org.springframework.stereotype.Service

@Service
class LLMReviewGenerationService(private val reviewGeneratorService: ReviewGeneratorService) {

    companion object {
        private const val REVIEWS_PER_BATCH = 10
    }

    fun generateReviews() {
        val reviewFileEntries = FileUtil.loadReviewsFile()

        val reviewResults = reviewFileEntries.reviews.flatMap { reviewFileEntry ->
            callLLM(reviewFileEntry)
        }

        FileUtil.writeReviewsResultFile(ReviewResults(reviewResults))
    }

    private fun callLLM(reviewFileEntry: ReviewsFileEntry): List<ReviewResult> =
        reviewFileEntry.reviews
            .chunked(REVIEWS_PER_BATCH)
            .flatMap { batch ->
                reviewGeneratorService
                    .generateReviews(reviewFileEntry.productName, batch)
                    .reviews
            }
}