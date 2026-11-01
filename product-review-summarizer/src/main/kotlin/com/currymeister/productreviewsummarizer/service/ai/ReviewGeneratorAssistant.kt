package com.currymeister.productreviewsummarizer.service.ai

import com.currymeister.productreviewsummarizer.dto.GeneratedReviewsDto
import com.currymeister.productreviewsummarizer.dto.ReviewSpecificationDto
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V
import dev.langchain4j.service.spring.AiService
import dev.langchain4j.service.spring.AiServiceWiringMode

@AiService(
    wiringMode = AiServiceWiringMode.EXPLICIT,
    chatModel = "reviewGeneratorChatModel"
)
interface ReviewGeneratorAssistant {
    @SystemMessage(fromResource = "prompt.md")
    @UserMessage("For the product {{product_name}}, generate reviews using following templates: {{review_templates}}")
    fun generateReviews(@V("product_name") productName: String, @V("review_templates") reviewTemplates: List<ReviewSpecificationDto>): GeneratedReviewsDto
}