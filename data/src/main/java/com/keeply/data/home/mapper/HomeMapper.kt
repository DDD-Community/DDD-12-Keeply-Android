package com.keeply.data.home.mapper

import com.keeply.data.home.dto.HomeFolderDto
import com.keeply.data.home.dto.HomeImageDto
import com.keeply.data.home.dto.HomeResponse
import com.keeply.domain.home.model.HomeData
import com.keeply.domain.home.model.HomeFolder
import com.keeply.domain.home.model.HomeImage

fun HomeResponse.toDomain(): HomeData {
    return HomeData(
        userId = userId,
        imageCount = imageCount,
        uncategorizedImageCount = uncategorizedImageCount,
        uncategorizedImageList = uncategorizedImageList.map { it.toDomain() },
        scheduledToDeleteImageCount = scheduledToDeleteImageCount,
        scheduledToDeleteImageList = scheduledToDeleteImageList.map { it.toDomain() },
        recentImages = recentImages.map { it.toDomain() },
        recentFolders = recentFolders.map { it.toDomain() },
        recentSavedImages = recentSavedImages.map { it.toDomain() }
    )
}

fun HomeImageDto.toDomain(): HomeImage {
    return HomeImage(
        imageId = imageId,
        presignedUrl = presignedUrl,
        tag = tag,
        insight = insight,
        updatedAt = updatedAt
    )
}

fun HomeFolderDto.toDomain(): HomeFolder {
    return HomeFolder(
        folderId = folderId,
        folderName = folderName,
        color = color,
        updatedAt = updatedAt,
        imageCount = imageCount
    )
}