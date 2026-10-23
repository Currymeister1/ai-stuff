package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.config.AppConfig
import com.currymeister.productreviewsummarizer.modal.ProductsConfigFile
import com.currymeister.productreviewsummarizer.modal.ReviewEntryDto
import com.currymeister.productreviewsummarizer.modal.ReviewEntryResponse
import com.currymeister.productreviewsummarizer.modal.ReviewFileEntries
import com.currymeister.productreviewsummarizer.modal.ReviewFileEntriesResponse
import com.currymeister.productreviewsummarizer.modal.ReviewsFileEntry
import com.currymeister.productreviewsummarizer.modal.ReviewsFileEntryResponse
import com.currymeister.productreviewsummarizer.util.FileUtil
import org.springframework.stereotype.Service

@Service
class ProductReviewJsonGeneratorService {

    companion object {
        private val REQUIRED_TOPICS = setOf("shipping", "customer-care", "quality", "payment")
    }


    fun createReviewJsonFile() {
        val productsFile = FileUtil.loadProductsConfigFile()
        val productConfigs = AppConfig.jsonInstance.decodeFromString<Set<ProductsConfigFile>>(productsFile)


        val reviews = productConfigs.map { generateReviewEntry(it) }.toList()
        val reviewFileEntries = ReviewFileEntries(reviews)

        FileUtil.writeReviewsFile(reviewFileEntries)
    }

    fun loadReviewsWithGeneratedTitles(): ReviewFileEntriesResponse {
        val reviewFileEntries = FileUtil.loadReviewsFile()
        val titlesByReviewId = FileUtil.loadReviewsResultFile()
            .reviews
            .associate { it.reviewId to it.reviewTitle }

        return ReviewFileEntriesResponse(
            reviews = reviewFileEntries.reviews.map { reviewFileEntry ->
                ReviewsFileEntryResponse(
                    productId = reviewFileEntry.productId,
                    productName = reviewFileEntry.productName,
                    reviews = reviewFileEntry.reviews.map { review ->
                        ReviewEntryResponse(
                            reviewId = review.reviewId,
                            reviewerAge = review.reviewerAge,
                            reviewStars = review.reviewStars,
                            requiredTopics = review.requiredTopics,
                            generatedReviewTitle = titlesByReviewId[review.reviewId],
                        )
                    },
                )
            },
        )
    }


    private fun generateReviewEntry(productConfig: ProductsConfigFile): ReviewsFileEntry {
        val reviews = productConfig.getReviewIdRange().map { reviewId ->
            ReviewEntryDto(
                reviewId = reviewId,
                reviewerAge = productConfig.getRandomAge(),
                reviewStars = productConfig.getRandomReviewStar(),
                requiredTopics = REQUIRED_TOPICS.shuffled().take(IntRange(1, REQUIRED_TOPICS.size).random())
            )
        }.toList()

        return ReviewsFileEntry(
            productId = productConfig.productId,
            productName = productConfig.productName,
            reviews = reviews
        )
    }
}