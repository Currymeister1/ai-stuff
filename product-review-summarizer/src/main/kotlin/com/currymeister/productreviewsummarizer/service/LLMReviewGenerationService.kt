package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.modal.ReviewEntryDto
import com.currymeister.productreviewsummarizer.modal.ReviewFileEntries
import com.currymeister.productreviewsummarizer.modal.ReviewsFileEntry
import dev.langchain4j.kotlin.model.chat.request.chatRequest
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.service.AiServices
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.io.File

@Service
class LLMReviewGenerationService(private val reviewGeneratorService: ReviewGeneratorService) {

    private val REVIEWS_FILE_NAME = "reviews.json"

    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }


    fun generateReviews() {
        val reviewFileEntries = loadReviewsFile()

        for (reviewFileEntry in reviewFileEntries.reviews) {

        }
    }

    private fun dos(reviewsFileEntry: ReviewsFileEntry) {
        val productName = reviewsFileEntry.productName
        val productId = reviewsFileEntry.productId

        val reviews = reviewsFileEntry.reviews
        reviewGeneratorService.chat()

        for (reviewEntry in reviewsFileEntry.reviews) {
        }
    }

    private fun loadApiKey(): String {
        val dotenv = dotenv()
        return dotenv["OPENAI_API_KEY"]
    }

    private fun loadReviewsFile(): ReviewFileEntries {
        val file = File(REVIEWS_FILE_NAME)
        val reviewsJson = if (file.isFile) file.readText() else loadClasspathResource(REVIEWS_FILE_NAME)
        return json.decodeFromString<ReviewFileEntries>(reviewsJson)
    }

    private fun loadClasspathResource(fileName: String): String {
        val inputStream = LLMReviewGenerationService::class.java.classLoader
            .getResourceAsStream(fileName)
            ?: error("Resource '$fileName' not found on the classpath")
        return inputStream.bufferedReader().use { it.readText() }
    }
}

fun main() {
}