package com.keeply.data.folder

import com.keeply.data.folder.mapper.toDomain
import com.keeply.data.folder.model.CreateFolderRequest
import com.keeply.data.folder.remote.FolderService
import com.keeply.domain.folder.model.Folder
import com.keeply.domain.folder.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderService: FolderService
) : FolderRepository {
    
    override suspend fun createFolder(folderName: String, color: String): Flow<Folder> = flow {
        val response = folderService.createFolder(
            CreateFolderRequest(
                folderName = folderName,
                color = color
            )
        )
        
        if (response.success == true && response.response != null) {
            emit(response.response.toDomain())
        } else {
            throw Exception(response.reason ?: "폴더 생성에 실패했습니다.")
        }
    }
}