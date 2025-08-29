package com.keeply.domain.image.model

data class ImageInfo(
    val imageId: Long,
    val presignedUrl: String,
    val insight: String,
    val tag: String,
    val isCategorized: Boolean,
    val scheduledDeleteAt: String,
    val daysUntilDeletion: Long
)