package com.keeply.data.image.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.dto.image.CreateImageRequest
import com.keeply.data.dto.image.ImageResponse
import com.keeply.data.screenshot.model.ImageSaveResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {
    @POST("api/images")
    suspend fun createImage(
        @Body request: CreateImageRequest
    ): BaseResponse<ImageResponse>
    
    @Multipart
    @POST("api/images/save")
    suspend fun saveImage(
        @Part file: MultipartBody.Part
    ): BaseResponse<ImageSaveResponse>
}