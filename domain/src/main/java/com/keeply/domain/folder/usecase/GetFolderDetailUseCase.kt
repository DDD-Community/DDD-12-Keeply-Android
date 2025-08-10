package com.keeply.domain.folder.usecase

import com.keeply.domain.folder.model.FolderImage
import com.keeply.domain.folder.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFolderDetailUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folderId: String): Flow<List<FolderImage>> {
        return folderRepository.getFolderDetail(folderId)
    }
}