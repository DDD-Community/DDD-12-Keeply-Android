package com.keeply.domain.image.usecase

import com.keeply.domain.image.model.ImageInfo
import com.keeply.domain.image.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageInfoUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageId: Long): Flow<ImageInfo> {
        return imageRepository.getImageInfo(imageId)
    }
}