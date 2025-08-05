package com.keeply.data.folder.mapper

import com.keeply.data.dto.folder.FolderImageResponse
import com.keeply.domain.folder.model.FolderImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun FolderImageResponse.toDomain(): FolderImage {
    return FolderImage(
        imageId = imageId ?: 0,
        presignedUrl = presignedUrl ?: "",
        tag = tag ?: "",
        insight = insight ?: "",
        updatedAt = updatedAt?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
        } ?: LocalDateTime.now()
    )
}