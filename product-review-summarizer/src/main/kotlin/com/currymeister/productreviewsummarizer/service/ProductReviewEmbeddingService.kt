package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.util.FileUtil.loadReviewsResultFile
import dev.langchain4j.data.document.Metadata
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import org.springframework.stereotype.Service

@Service
class ProductReviewEmbeddingService (
    private val embeddingStore: EmbeddingStore<TextSegment>,
    private val embeddingModel: EmbeddingModel
){
    fun embedProductReviews() {
        val reviews = loadReviewsResultFile().reviews
        val segments = reviews.map { review ->

            TextSegment.from(
                """
                Product: ${review.reviewTitle}
                Description: ${review.reviewText}
                """.trimIndent(),
                Metadata.from(mapOf("reviewId" to review.reviewId))
            )
        }

        val embeddings = embeddingModel.embedAll(segments).content()

        embeddingStore.addAll(embeddings, segments)
    }
}