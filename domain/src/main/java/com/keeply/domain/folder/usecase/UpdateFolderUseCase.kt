package com.keeply.domain.folder.usecase

import com.keeply.domain.folder.model.Folder
import com.keeply.domain.folder.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(
        folderId: Long,
        folderName: String,
        color: String
    ): Flow<Folder> {
        return folderRepository.updateFolder(folderId, folderName, color)
    }
}