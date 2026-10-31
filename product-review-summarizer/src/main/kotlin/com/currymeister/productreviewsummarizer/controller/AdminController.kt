package com.currymeister.productreviewsummarizer.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {

    @GetMapping("/admin")
    fun admin(): String = "forward:/admin.html"

    @GetMapping("/admin/products/new")
    fun createProductPage(): String = "forward:/create-product.html"
}
