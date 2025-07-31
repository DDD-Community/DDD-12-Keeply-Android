package com.keeply.data.screenshot.model

import kotlinx.serialization.Serializable

@Serializable
data class ScanImageRequest(
    val isNew: Boolean,
    val imageId: Long?,
    val file: String?
)