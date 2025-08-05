package com.keeply.domain.folder.usecase

import com.keeply.domain.folder.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long): Flow<Boolean> {
        return folderRepository.deleteFolder(folderId)
    }
}