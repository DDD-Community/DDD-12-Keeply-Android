package com.keeply.data.folder.model

import com.keeply.domain.folder.model.Folder
import kotlinx.serialization.Serializable

@Serializable
data class FolderListResponse(
    val folderList: List<FolderData>? = null
)

@Serializable
data class FolderData(
    val folderId: Long? = null,
    val folderName: String? = null,
    val color: String? = null
)