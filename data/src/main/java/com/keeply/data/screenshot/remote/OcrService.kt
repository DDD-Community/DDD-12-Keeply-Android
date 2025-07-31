package com.keeply.data.screenshot.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.screenshot.model.ScanImageRequest
import com.keeply.data.screenshot.model.ScanAnalyzeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OcrService {
    @POST("api/ocr/analyze")
    suspend fun analyzeImage(@Body request: ScanImageRequest): BaseResponse<ScanAnalyzeResponse>
}