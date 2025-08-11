package com.keeply.data.image.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.dto.image.CreateImageRequest
import com.keeply.data.dto.image.ImageResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageService {
    @POST("api/images")
    suspend fun createImage(
        @Body request: CreateImageRequest
    ): BaseResponse<ImageResponse>
}