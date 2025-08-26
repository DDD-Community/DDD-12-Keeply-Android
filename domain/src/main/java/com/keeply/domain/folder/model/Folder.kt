package com.keeply.domain.folder.model

data class Folder(
    val folderId: Long,
    val folderName: String,
    val color: String,
    val imageCount: Int,
    val updatedAt: String,

    // 폴더 생성 시 중복 폴더인 경우
    val isDuplicate: Boolean = false,
    val duplicatedMessage: String? = null
)