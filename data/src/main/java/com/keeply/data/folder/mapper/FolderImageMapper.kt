package com.keeply.data.folder.mapper

import com.keeply.data.dto.folder.FolderImageResponse
import com.keeply.domain.folder.model.FolderImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun FolderImageResponse.toDomain(): FolderImage {
    return FolderImage(
        imageId = imageId ?: 0,
        presignedUrl = presignedUrl ?: "",
        insight = insight ?: "",
        tag = tag ?: "",
        isCategorized = isCategorized ?: false,
        scheduledDeleteAt = scheduledDeleteAt?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
        },
        daysUntilDeletion = daysUntilDeletion ?: 0
    )
}