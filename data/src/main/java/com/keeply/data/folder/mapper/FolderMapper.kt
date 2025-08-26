package com.keeply.data.folder.mapper

import com.keeply.data.folder.model.FolderData
import com.keeply.data.folder.model.FolderResponse
import com.keeply.domain.extend.default
import com.keeply.domain.folder.model.Folder

fun FolderResponse?.toDomain(): Folder {
    return Folder(
        folderId = this?.folderId.default(),
        folderName = this?.folderName.default(),
        color = this?.color.default(),
        imageCount = 0,
        updatedAt = "",
        isDuplicate = this?.isDuplicate ?: false,
        duplicatedMessage = this?.duplicatedMessage.default()
    )
}

fun FolderData.toDomain(): Folder {
    return Folder(
        folderId = folderId.default(),
        folderName = folderName.default(),
        color = color.default(),
        imageCount = imageCount.default(),
        updatedAt = updatedAt.default()
    )
}