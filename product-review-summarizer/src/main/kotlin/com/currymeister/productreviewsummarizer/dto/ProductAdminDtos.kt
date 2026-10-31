package com.currymeister.productreviewsummarizer.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CreateProductRequest(
    @field:NotBlank(message = "Product name is required")
    val productName: String,

    @field:NotBlank(message = "Category is required")
    val category: String,

    @field:Valid
    val reviewConfig: ReviewConfigRequest,
)

data class ReviewConfigRequest(
    @field:Min(value = 1, message = "Minimum reviewer age must be at least 1")
    val ageMin: Int,

    @field:Min(value = 1, message = "Maximum reviewer age must be at least 1")
    val ageMax: Int,

    @field:Min(value = 0, message = "Review count cannot be negative")
    val reviewCount: Int,

    @field:Min(value = 1, message = "General review stars must be between 1 and 5")
    @field:Max(value = 5, message = "General review stars must be between 1 and 5")
    val generalReviewStars: Int,
)

data class ProductCreatedResponse(
    val productId: Int,
    val productName: String,
    val category: String,
    val reviewConfig: ReviewConfigResponse,
)

data class ReviewConfigResponse(
    val ageMin: Int,
    val ageMax: Int,
    val reviewCount: Int,
    val generalReviewStars: Int,
)
