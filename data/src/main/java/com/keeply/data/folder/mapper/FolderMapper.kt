package com.keeply.data.folder.mapper

import com.keeply.data.folder.model.FolderResponse
import com.keeply.domain.extend.default
import com.keeply.domain.folder.model.Folder

fun FolderResponse?.toDomain(): Folder {
    return Folder(
        folderId = this?.folderId.default(),
        folderName = this?.folderName.default(),
        color = this?.color.default()
    )
}