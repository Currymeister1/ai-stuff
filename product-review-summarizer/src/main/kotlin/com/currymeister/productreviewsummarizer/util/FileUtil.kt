package com.currymeister.productreviewsummarizer.util

import com.currymeister.productreviewsummarizer.config.AppConfig
import com.currymeister.productreviewsummarizer.modal.ReviewFileEntries
import com.currymeister.productreviewsummarizer.service.LLMReviewGenerationService
import java.io.File

object FileUtil {
    private const val REVIEWS_FILE_NAME = "reviews.json"
    private const val PRODUCTS_FILE_NAME = "products_config.json"
    private val json = AppConfig.jsonInstance


    fun loadReviewsFile(): ReviewFileEntries {
        val file = File(REVIEWS_FILE_NAME)
        val reviewsJson = if (file.isFile) file.readText() else loadClasspathResource(REVIEWS_FILE_NAME)
        return json.decodeFromString<ReviewFileEntries>(reviewsJson)
    }

    fun loadProductsConfigFile(): String = loadClasspathResource(PRODUCTS_FILE_NAME)

    fun writeReviewsFile(reviewFileEntries: ReviewFileEntries) {
        val file = File(REVIEWS_FILE_NAME)
        file.parentFile?.mkdirs()
        file.writeText(json.encodeToString(reviewFileEntries))
    }


    private fun loadClasspathResource(fileName: String): String {
        val inputStream = LLMReviewGenerationService::class.java.classLoader
            .getResourceAsStream(fileName)
            ?: error("Resource '$fileName' not found on the classpath")
        return inputStream.bufferedReader().use { it.readText() }
    }
}