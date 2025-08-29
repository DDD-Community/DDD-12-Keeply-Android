package com.keeply.data.screenshot.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageSaveResponse(
    val imageId: Long
)