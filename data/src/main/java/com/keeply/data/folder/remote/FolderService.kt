package com.keeply.data.folder.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.dto.folder.FolderDetailResponse
import com.keeply.data.dto.folder.UpdateFolderRequest
import com.keeply.data.folder.model.CreateFolderRequest
import com.keeply.data.folder.model.FolderListResponse
import com.keeply.data.folder.model.FolderResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FolderService {

    @POST("api/folders")
    suspend fun createFolder(@Body request: CreateFolderRequest): BaseResponse<FolderResponse>

    @GET("api/folders")
    suspend fun getFolders(): BaseResponse<FolderListResponse>

    @GET("api/folders/{folderId}")
    suspend fun getFolderDetail(@Path("folderId") folderId: String): BaseResponse<FolderDetailResponse>

    @PUT("api/folders/{folderId}")
    suspend fun updateFolder(
        @Path("folderId") folderId: Long,
        @Body request: UpdateFolderRequest
    ): BaseResponse<FolderResponse>

    @DELETE("api/folders/{folderId}")
    suspend fun deleteFolder(
        @Path("folderId") folderId: Long
    ): BaseResponse<Map<String, String>>
}