package com.currymeister.productreviewsummarizer.service.ai

import dev.langchain4j.service.Result
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.spring.AiService
import dev.langchain4j.service.spring.AiServiceWiringMode

@AiService(
    wiringMode = AiServiceWiringMode.EXPLICIT,
    chatModel = "reviewSummarizerChatModel",
    contentRetriever = "reviewContentRetriever"
)
interface ProductSummarizerAssistant {
    @SystemMessage(
        """
 You are a product review analyst.

    Answer the user's question using the retrieved reviews.

    If reviews are relevant, summarize their findings.
    If reviews are not relevant to the question, say:
    "No relevant reviews found."

    Do not invent information that is not present in the reviews.
        """
    )
    fun summarize(question: String): Result<String>
}