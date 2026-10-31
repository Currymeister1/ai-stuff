package com.currymeister.productreviewsummarizer.controller.admin

import com.currymeister.productreviewsummarizer.dto.CreateProductRequest
import com.currymeister.productreviewsummarizer.dto.ProductCreatedResponse
import com.currymeister.productreviewsummarizer.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/products")
class ProductAdminController(
    private val productService: ProductService,
) {

    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: CreateProductRequest
    ): ResponseEntity<ProductCreatedResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request))
}
