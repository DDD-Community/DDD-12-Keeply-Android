package com.keeply.domain.image.usecase

import com.keeply.domain.image.model.Image
import com.keeply.domain.image.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(
        isCached: Boolean,
        cachedImageId: String,
        imageId: Long,
        imageInsight: String,
        folderId: Long,
        tag: String
    ): Flow<Image> {
        return imageRepository.createImage(
            isCached = isCached,
            cachedImageId = cachedImageId,
            imageId = imageId,
            imageInsight = imageInsight,
            folderId = folderId,
            tag = tag
        )
    }
}