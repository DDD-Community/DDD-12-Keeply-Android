package com.keeply.data.image.mapper

import com.keeply.data.dto.image.ImageInfoResponse
import com.keeply.data.dto.image.ImageResponse
import com.keeply.domain.image.model.Image
import com.keeply.domain.image.model.ImageInfo

fun ImageResponse.toDomain(): Image {
    return Image(
        imageId = imageId ?: 0
    )
}

fun ImageInfoResponse.toDomain(): ImageInfo {
    return ImageInfo(
        imageId = imageId ?: 0,
        presignedUrl = presignedUrl ?: "",
        insight = insight ?: "",
        tag = tag ?: "",
        isCategorized = isCategorized ?: false,
        scheduledDeleteAt = scheduledDeleteAt ?: "",
        daysUntilDeletion = daysUntilDeletion ?: 0,
    )
}