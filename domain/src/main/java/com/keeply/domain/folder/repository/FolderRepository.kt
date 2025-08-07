package com.keeply.domain.folder.repository

import com.keeply.domain.folder.model.Folder
import com.keeply.domain.folder.model.FolderImage
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(folderName: String, color: String): Flow<Folder>
    suspend fun getFolders(): Flow<List<Folder>>
    suspend fun getFolderDetail(folderId: Long): Flow<List<FolderImage>>
}