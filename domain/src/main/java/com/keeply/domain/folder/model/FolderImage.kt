package com.keeply.domain.folder.model

import java.time.LocalDate

data class FolderImage(
    val imageId: Long,
    val presignedUrl: String,
    val insight: String,
    val tag: String,
    val isCategorized: Boolean,
    val scheduledDeleteAt: LocalDate?,
    val daysUntilDeletion: Int
)