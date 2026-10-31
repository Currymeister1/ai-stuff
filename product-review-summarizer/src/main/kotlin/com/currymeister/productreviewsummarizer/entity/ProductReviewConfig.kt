package com.currymeister.productreviewsummarizer.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kotlin.random.Random

@Entity
@Table(name = "product_review_config")
class ProductReviewConfig(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    var product: Product? = null,

    @Column(name = "age_min", nullable = false)
    var ageMin: Int = 18,

    @Column(name = "age_max", nullable = false)
    var ageMax: Int = 65,

    @Column(name = "review_count", nullable = false)
    var reviewCount: Int = 0,

    @Column(name = "general_review_stars", nullable = false)
    var generalReviewStars: Int = 3,
) {
    companion object {
        private val ALL_STARS = listOf(1, 2, 3, 4, 5)
    }

    fun randomAge(): Int = IntRange(ageMin, ageMax).random()

    fun randomStar(matchChance: Double = 0.7): Int =
        if (Random.nextDouble() < matchChance) {
            generalReviewStars
        } else {
            (ALL_STARS - generalReviewStars).random()
        }
}
