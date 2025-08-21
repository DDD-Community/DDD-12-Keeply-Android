package com.keeply.data.screenshot.model

import kotlinx.serialization.Serializable

@Serializable
data class ScanAnalyzeResponse(
    val cachedImageId: String?,
    val detectedText: String?
)