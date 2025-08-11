package com.keeply.data.image.mapper

import com.keeply.data.dto.image.ImageResponse
import com.keeply.domain.image.model.Image

fun ImageResponse.toDomain(): Image {
    return Image(
        imageId = imageId ?: 0
    )
}