package com.keeply.domain.home.model

data class HomeFolder(
    val folderId: Long,
    val folderName: String,
    val color: String,
    val updatedAt: String,
    val imageCount: Int
)