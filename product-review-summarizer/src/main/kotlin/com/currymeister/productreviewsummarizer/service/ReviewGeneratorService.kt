package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.modal.ReviewEntryDto
import com.currymeister.productreviewsummarizer.modal.ReviewResults
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V
import dev.langchain4j.service.spring.AiService

@AiService
interface ReviewGeneratorService {
    @SystemMessage(fromResource = "prompt.md")
    @UserMessage("For the product {{product_name}}, generate reviews using following templates: {{review_templates}}")
    fun generateReviews(@V("product_name") productName: String, @V("review_templates") reviewTemplates: List<ReviewEntryDto>): ReviewResults
}