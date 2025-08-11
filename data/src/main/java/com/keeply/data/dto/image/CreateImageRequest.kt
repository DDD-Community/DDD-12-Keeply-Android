package com.keeply.data.dto.image

import kotlinx.serialization.Serializable

@Serializable
data class CreateImageRequest(
    val isCached: Boolean,
    val cachedImageId: String,
    val imageId: Long = 0,
    val imageInsight: String,
    val folderId: Long = 0,
    val tag: String = ""
)