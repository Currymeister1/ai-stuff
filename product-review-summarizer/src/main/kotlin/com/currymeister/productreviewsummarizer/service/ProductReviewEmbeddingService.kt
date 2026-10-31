package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.entity.ProductReview
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
    fun embedProductReviews(batchSize: Int = 100) {
        productReviewRepository.findAllWithGeneratedText() // join fetch product
            .chunked(batchSize)
            .forEach { batch ->
                val segments = batch.map(::toSegment)
                val embeddings = embeddingModel.embedAll(segments).content()
                embeddingStore.addAll(embeddings, segments)
            }
    }

    private fun toSegment(review: ProductReview): TextSegment =
        TextSegment.from(
            "${review.title}\n${review.text}",
            Metadata.from(
                mapOf(
                    "reviewId" to review.id.toString(),
                    "productId" to review.product.id.toString(),
                    "category" to review.product.category,
                ),
            ),
        )
}
