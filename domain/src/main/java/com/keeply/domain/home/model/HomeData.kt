package com.keeply.domain.home.model

data class HomeData(
    val userId: Long,
    val imageCount: Int,
    val uncategorizedImageCount: Int,
    val uncategorizedImageList: List<HomeImage>,
    val scheduledToDeleteImageCount: Int,
    val scheduledToDeleteImageList: List<HomeImage>,
    val recentImages: List<HomeImage>,
    val recentFolders: List<HomeFolder>,
    val recentSavedImages: List<HomeImage>
)