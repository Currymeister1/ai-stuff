package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.repository.ProductReviewRepository
import dev.langchain4j.data.document.Metadata
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import org.springframework.stereotype.Service

@Service
class ProductReviewEmbeddingService(
    private val embeddingStore: EmbeddingStore<TextSegment>,
    private val embeddingModel: EmbeddingModel,
    private val productReviewRepository: ProductReviewRepository,
) {
    fun embedProductReviews() {
        val segments = productReviewRepository.findAllWithGeneratedText().map { review ->
            TextSegment.from(
                """
                Product: ${review.title}
                Description: ${review.text}
                """.trimIndent(),
                Metadata.from(mapOf("reviewId" to requireNotNull(review.id))),
            )
        }

        if (segments.isEmpty()) return

        val embeddings = embeddingModel.embedAll(segments).content()

        embeddingStore.addAll(embeddings, segments)
    }
}
