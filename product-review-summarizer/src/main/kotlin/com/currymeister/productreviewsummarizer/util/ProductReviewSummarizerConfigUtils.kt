package com.currymeister.productreviewsummarizer.util

import io.github.cdimascio.dotenv.dotenv

object ProductReviewSummarizerConfigUtils {
    fun loadApiKey(): String {
        val dotenv = dotenv()
        return dotenv["OPENAI_API_KEY"]
    }
}