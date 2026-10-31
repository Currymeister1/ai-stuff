package com.currymeister.productreviewsummarizer.repository

import com.currymeister.productreviewsummarizer.entity.ProductReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductReviewRepository : JpaRepository<ProductReview, Int> {

    @Query(
        """
        select distinct review
        from ProductReview review
        join fetch review.product
        left join fetch review.requiredTopics
        order by review.id
        """
    )
    fun findAllWithProductAndTopics(): List<ProductReview>

    @Query(
        """
        select distinct review
        from ProductReview review
        join fetch review.product
        left join fetch review.requiredTopics
        where review.id = :id
        """
    )
    fun findWithProductAndTopicsById(@Param("id") id: Int): ProductReview?

    @Query(
        """
        select distinct review
        from ProductReview review
        join fetch review.product
        left join fetch review.requiredTopics
        where review.title is null or review.text is null
        order by review.id
        """
    )
    fun findAllMissingGeneratedText(): List<ProductReview>

    @Query(
        """
        select review
        from ProductReview review
        where review.title is not null and review.text is not null
        order by review.id
        """
    )
    fun findAllWithGeneratedText(): List<ProductReview>
}
