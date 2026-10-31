package com.currymeister.productreviewsummarizer.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "product_name", nullable = false)
    var productName: String = "",

    @Column(nullable = false)
    var category: String = "",

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    var reviews: MutableList<ProductReview> = mutableListOf(),
)
