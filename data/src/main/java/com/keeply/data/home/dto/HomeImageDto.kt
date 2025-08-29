package com.keeply.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeImageDto(
    @SerialName("imageId")
    val imageId: Long,
    @SerialName("presignedUrl")
    val presignedUrl: String,
    @SerialName("tag")
    val tag: String?,
    @SerialName("tagColor")
    val tagColor: String?,
    @SerialName("insight")
    val insight: String?,
    @SerialName("updatedAt")
    val updatedAt: String
)