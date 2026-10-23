package com.currymeister.productreviewsummarizer.config

import kotlinx.serialization.json.Json

object AppConfig {
    val jsonInstance = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
}