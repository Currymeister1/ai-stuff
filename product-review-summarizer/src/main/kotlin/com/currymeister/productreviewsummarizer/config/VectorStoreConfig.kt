package com.currymeister.productreviewsummarizer.config

import com.currymeister.productreviewsummarizer.util.ProductReviewSummarizerConfigUtils
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.model.openai.OpenAiEmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@EnableConfigurationProperties(VectorStoreProperties::class)
class VectorStoreConfig {

    @Bean
    fun embeddingModel(): EmbeddingModel = OpenAiEmbeddingModel
        .builder()
        .apiKey(ProductReviewSummarizerConfigUtils.loadApiKey())
        .modelName("text-embedding-3-small")
        .build()

    @Bean
    fun embeddingStore(
        dataSource: DataSource,
        properties: VectorStoreProperties,
    ): EmbeddingStore<TextSegment> =
        PgVectorEmbeddingStore.datasourceBuilder()
            .datasource(dataSource)
            .table(properties.table)
            .dimension(properties.dimension)
            .createTable(true)
            .dropTableFirst(false)
            .useIndex(true)
            .indexListSize(100)
            .build()
}

@ConfigurationProperties(prefix = "app.vector")
data class VectorStoreProperties(val table: String, val dimension: Int)