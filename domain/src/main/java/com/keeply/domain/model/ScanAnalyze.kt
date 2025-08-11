package com.keeply.domain.model

data class ScanAnalyze(
    val cachedImageId: String?,
    val detectedText: String?,
    val recommendedTags: List<String>?
)