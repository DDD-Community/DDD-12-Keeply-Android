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
    val tag: String? = null,
    val insight: String? = null,
    val updatedAt: String? = null
)