package com.currymeister.productreviewsummarizer.service

import com.currymeister.productreviewsummarizer.dto.CreateProductRequest
import com.currymeister.productreviewsummarizer.dto.ProductCreatedResponse
import com.currymeister.productreviewsummarizer.dto.ReviewConfigResponse
import com.currymeister.productreviewsummarizer.entity.Product
import com.currymeister.productreviewsummarizer.entity.ProductReviewConfig
import com.currymeister.productreviewsummarizer.repository.ProductRepository
import com.currymeister.productreviewsummarizer.repository.ProductReviewConfigRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productReviewConfigRepository: ProductReviewConfigRepository,
) {

    @Transactional
    fun createProduct(request: CreateProductRequest): ProductCreatedResponse {
        val config = request.reviewConfig
        require(config.ageMin <= config.ageMax) {
            "Minimum reviewer age cannot be greater than maximum reviewer age"
        }

        val product = productRepository.save(
            Product(
                productName = request.productName.trim(),
                category = request.category.trim(),
            )
        )

        val savedConfig = productReviewConfigRepository.save(
            ProductReviewConfig(
                product = product,
                ageMin = config.ageMin,
                ageMax = config.ageMax,
                reviewCount = config.reviewCount,
                generalReviewStars = config.generalReviewStars,
            )
        )

        return ProductCreatedResponse(
            productId = requireNotNull(product.id),
            productName = product.productName,
            category = product.category,
            reviewConfig = ReviewConfigResponse(
                ageMin = savedConfig.ageMin,
                ageMax = savedConfig.ageMax,
                reviewCount = savedConfig.reviewCount,
                generalReviewStars = savedConfig.generalReviewStars,
            ),
        )
    }
}
