package com.currymeister.productreviewsummarizer.repository

import com.currymeister.productreviewsummarizer.entity.ProductReviewConfig
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductReviewConfigRepository : JpaRepository<ProductReviewConfig, Int> {

    @Query("select config from ProductReviewConfig config join fetch config.product")
    fun findAllWithProduct(): List<ProductReviewConfig>
}
