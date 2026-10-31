package com.currymeister.productreviewsummarizer.repository

import com.currymeister.productreviewsummarizer.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {
    fun findAllByOrderByIdAsc(): List<Product>
}
