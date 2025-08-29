package com.keeply.data.dto.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val imageId: Long? = null
)

@Serializable
data class ImageInfoResponse(
    val imageId: Long? = null,
    val presignedUrl: String? = null,
    val insight: String? = null,
    val tag: String? = null,
    val isCategorized: Boolean? = null,
    val scheduledDeleteAt: String? = null,
    val daysUntilDeletion: Long? = null
)