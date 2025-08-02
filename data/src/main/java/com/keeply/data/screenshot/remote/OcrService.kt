package com.keeply.data.screenshot.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.screenshot.model.ScanAnalyzeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OcrService {

    @Multipart
    @POST("api/ocr/analyze")
    suspend fun analyzeImage(
        @Part("isNew") isNew: RequestBody,
        @Part("imageId") imageId: RequestBody?,
        @Part file: MultipartBody.Part
    ): BaseResponse<ScanAnalyzeResponse>
}