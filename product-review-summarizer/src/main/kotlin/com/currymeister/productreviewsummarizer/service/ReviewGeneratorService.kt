package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.modal.ReviewResult
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.spring.AiService

@AiService
interface ReviewGeneratorService {
    @SystemMessage(fromResource = "prompt.md")
    fun chat(message: String): ReviewResult
}