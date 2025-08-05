package com.keeply.data.folder

import com.keeply.data.core.extension.toException
import com.keeply.data.folder.mapper.toDomain
import com.keeply.data.folder.model.CreateFolderRequest
import com.keeply.data.folder.remote.FolderService
import com.keeply.domain.folder.model.Folder
import com.keeply.domain.folder.model.FolderImage
import com.keeply.domain.folder.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderService: FolderService,
    private val json: Json
) : FolderRepository {
    
    override suspend fun createFolder(folderName: String, color: String): Flow<Folder> = flow {
        try {
            val response = folderService.createFolder(
                CreateFolderRequest(
                    folderName = folderName,
                    color = color
                )
            )
            
            if (response.success == true && response.response != null) {
                emit(response.response.toDomain())
            } else {
                throw Exception(response.reason)
            }
        } catch (e: HttpException) {
            throw e.toException(json)
        }
    }

    override suspend fun getFolders(): Flow<List<Folder>> = flow {
        try {
            val response = folderService.getFolders()
            
            if (response.success == true && response.response != null) {
                val folders = response.response.folderList?.map { it.toDomain() } ?: emptyList()
                emit(folders)
            } else {
                throw Exception(response.reason)
            }
        } catch (e: HttpException) {
            throw e.toException(json)
        }
    }

    override suspend fun getFolderDetail(folderId: Long): Flow<List<FolderImage>> = flow {
        try {
            val response = folderService.getFolderDetail(folderId)
            
            if (response.success == true && response.response != null) {
                val images = response.response.imageList?.map { it.toDomain() } ?: emptyList()
                emit(images)
            } else {
                throw Exception(response.reason)
            }
        } catch (e: HttpException) {
            throw e.toException(json)
        }
    }
}