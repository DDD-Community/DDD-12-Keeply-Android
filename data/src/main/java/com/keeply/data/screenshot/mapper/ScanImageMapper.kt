package com.keeply.data.screenshot.mapper

import com.keeply.data.screenshot.model.ScanImageRequest
import com.keeply.domain.model.ScanImage

fun ScanImage.toRequest() = ScanImageRequest(
    isNew = isNew,
    imageId = imageId,
    file = file
)