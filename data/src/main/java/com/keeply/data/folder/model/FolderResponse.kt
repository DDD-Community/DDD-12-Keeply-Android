package com.keeply.data.folder.model

import kotlinx.serialization.Serializable

@Serializable
data class FolderResponse(
    val folderId: Long?,
    val folderName: String?,
    val color: String?
)