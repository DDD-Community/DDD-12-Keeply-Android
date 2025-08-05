package com.keeply.domain.folder.model

import java.time.LocalDateTime

data class FolderImage(
    val imageId: Long,
    val presignedUrl: String,
    val tag: String,
    val insight: String,
    val updatedAt: LocalDateTime
)