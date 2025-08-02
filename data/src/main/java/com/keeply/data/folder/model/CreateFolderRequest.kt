package com.keeply.data.folder.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderRequest(
    val folderName: String,
    val color: String
)