package com.keeply.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeFolderDto(
    @SerialName("folderId")
    val folderId: Long,
    @SerialName("color")
    val color: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("imageCount")
    val imageCount: Int
)