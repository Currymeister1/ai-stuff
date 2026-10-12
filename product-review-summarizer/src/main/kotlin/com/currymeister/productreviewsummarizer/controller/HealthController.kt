package com.currymeister.productreviewsummarizer.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun health(): ResponseEntity<Map<String, String>> = ResponseEntity.ok(
        mapOf(
            "status" to "Ok",
            "message" to "Everything is running"
        )
    )
}