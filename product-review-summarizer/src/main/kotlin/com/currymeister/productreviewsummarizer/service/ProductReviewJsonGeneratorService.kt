package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.modal.ProductsConfigFile
import com.currymeister.productreviewsummarizer.modal.ReviewEntryDto
import com.currymeister.productreviewsummarizer.modal.ReviewFileEntries
import com.currymeister.productreviewsummarizer.modal.ReviewsFileEntry
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.io.File

@Service
class ProductReviewJsonGeneratorService {

    companion object {
        private const val PRODUCTS_FILE_NAME = "products_config.json"
        private const val REVIEWS_FILE_NAME = "reviews.json"
        private const val REVIEWS_OUTPUT_PATH = "src/main/resources/$REVIEWS_FILE_NAME"

        private val REQUIRED_TOPICS = setOf("shipping", "customer-care", "quality", "payment")
    }

    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    fun createReviewJsonFile() {
        val productsFile = loadProductsConfigFile()
        val productConfigs = json.decodeFromString<Set<ProductsConfigFile>>(productsFile)


        val reviews = productConfigs.map { generateReviewEntry(it)}.toList()
        val reviewFileEntries = ReviewFileEntries(reviews)

        writeReviewsFile(reviewFileEntries)
    }

    fun loadReviewsFile(): ReviewFileEntries {
        val file = File(REVIEWS_OUTPUT_PATH)
        val reviewsJson = if (file.isFile) file.readText() else loadClasspathResource(REVIEWS_FILE_NAME)
        return json.decodeFromString<ReviewFileEntries>(reviewsJson)
    }

    private fun writeReviewsFile(reviewFileEntries: ReviewFileEntries) {
        val file = File(REVIEWS_OUTPUT_PATH)
        file.parentFile?.mkdirs()
        file.writeText(json.encodeToString(reviewFileEntries))
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

    private fun loadProductsConfigFile(): String = loadClasspathResource(PRODUCTS_FILE_NAME)

    private fun loadClasspathResource(fileName: String): String {
        val inputStream = ProductReviewJsonGeneratorService::class.java.classLoader
            .getResourceAsStream(fileName)
            ?: error("Resource '$fileName' not found on the classpath")
        return inputStream.bufferedReader().use { it.readText() }
    }
}