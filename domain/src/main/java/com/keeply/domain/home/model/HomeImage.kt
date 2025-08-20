package com.keeply.domain.home.model

data class HomeImage(
    val imageId: Long,
    val presignedUrl: String,
    val tag: String,
    val tagColor: String,
    val insight: String,
    val updatedAt: String
)