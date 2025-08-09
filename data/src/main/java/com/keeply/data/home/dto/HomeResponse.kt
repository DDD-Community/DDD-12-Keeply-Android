package com.keeply.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("imageCount")
    val imageCount: Int,
    @SerialName("uncategorizedImageCount")
    val uncategorizedImageCount: Int,
    @SerialName("uncategorizedImageList")
    val uncategorizedImageList: List<HomeImageDto>,
    @SerialName("scheduledToDeleteImageCount")
    val scheduledToDeleteImageCount: Int,
    @SerialName("scheduledToDeleteImageList")
    val scheduledToDeleteImageList: List<HomeImageDto>,
    @SerialName("recentImages")
    val recentImages: List<HomeImageDto>,
    @SerialName("recentFolders")
    val recentFolders: List<HomeFolderDto>,
    @SerialName("recentSavedImages")
    val recentSavedImages: List<HomeImageDto>
)