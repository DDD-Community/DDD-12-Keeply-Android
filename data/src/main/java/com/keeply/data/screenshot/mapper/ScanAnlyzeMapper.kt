package com.keeply.data.screenshot.mapper

import com.keeply.data.screenshot.model.ScanAnalyzeResponse
import com.keeply.domain.extend.default
import com.keeply.domain.model.ScanAnalyze

fun ScanAnalyzeResponse?.toDomain() = ScanAnalyze(
    cachedImageId = this?.cachedImageId.default(),
    detectedText = this?.detectedText.default()
)