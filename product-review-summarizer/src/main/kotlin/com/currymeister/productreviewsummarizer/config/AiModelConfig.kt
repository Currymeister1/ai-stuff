package com.currymeister.productreviewsummarizer.config

import dev.langchain4j.model.chat.Capability
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.openai.OpenAiChatModel
import io.github.cdimascio.dotenv.dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiModelConfig {

    @Bean
    fun chatModel(): ChatModel {
        val apiKey = loadApiKey() // Your custom programmatic key loader

        return OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gpt-4o-mini")
            .temperature(0.9)
            .frequencyPenalty(1.5)
            .supportedCapabilities(setOf(Capability.RESPONSE_FORMAT_JSON_SCHEMA))
            .responseFormat(ResponseFormat.JSON)
            .strictJsonSchema(true)
            .logRequests(true)
            .logResponses(true)
            .build()
    }

    private fun loadApiKey(): String {
        val dotenv = dotenv()
        return dotenv["OPENAI_API_KEY"]
    }
}