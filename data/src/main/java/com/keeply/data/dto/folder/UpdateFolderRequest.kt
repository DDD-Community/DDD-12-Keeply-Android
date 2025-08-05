package com.keeply.data.dto.folder

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFolderRequest(
    val folderName: String,
    val color: String
)