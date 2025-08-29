package com.keeply.domain.image.repository

import com.keeply.domain.image.model.Image
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ImageRepository {
    suspend fun createImage(
        isCached: Boolean,
        cachedImageId: String,
        imageId: Long,
        imageInsight: String,
        folderId: Long,
        tag: String
    ): Flow<Image>
    
    suspend fun saveImage(file: File): Flow<Long>
}