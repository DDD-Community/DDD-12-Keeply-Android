package com.keeply.data.folder.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.folder.model.CreateFolderRequest
import com.keeply.data.folder.model.FolderListResponse
import com.keeply.data.folder.model.FolderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FolderService {

    @POST("api/folders")
    suspend fun createFolder(@Body request: CreateFolderRequest): BaseResponse<FolderResponse>

    @GET("api/folders")
    suspend fun getFolders(): BaseResponse<FolderListResponse>

}