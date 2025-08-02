package com.keeply.domain.folder.repository

import com.keeply.domain.folder.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(folderName: String, color: String): Flow<Folder>
}