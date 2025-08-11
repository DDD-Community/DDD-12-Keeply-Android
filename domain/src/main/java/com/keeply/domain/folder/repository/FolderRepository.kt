package com.keeply.domain.folder.repository

import com.keeply.domain.folder.model.Folder
import com.keeply.domain.folder.model.FolderImage
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(folderName: String, color: String): Flow<Folder>
    suspend fun getFolders(): Flow<List<Folder>>
    suspend fun getFolderDetail(folderId: String): Flow<List<FolderImage>>
    suspend fun updateFolder(folderId: Long, folderName: String, color: String): Flow<Folder>
    suspend fun deleteFolder(folderId: Long): Flow<Boolean>
}