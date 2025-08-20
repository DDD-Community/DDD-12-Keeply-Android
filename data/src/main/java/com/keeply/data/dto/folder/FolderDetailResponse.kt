package com.keeply.data.dto.folder

import kotlinx.serialization.Serializable

@Serializable
data class FolderDetailResponse(
    val imageList: List<FolderImageResponse>? = null
)

@Serializable
data class FolderImageResponse(
    val imageId: Long? = null,
    val presignedUrl: String? = null,
    val insight: String? = null,
    val tag: String? = null,
    val tagColor: String? = null,
    val isCategorized: Boolean? = null,
    val scheduledDeleteAt: String? = null,
    val daysUntilDeletion: Int? = null
)