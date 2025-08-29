package com.keeply.domain.image.usecase

import com.keeply.domain.image.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

interface SaveImageUseCase {
    suspend operator fun invoke(file: File): Flow<Long>
}

class SaveImageUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : SaveImageUseCase {
    override suspend fun invoke(file: File): Flow<Long> {
        return imageRepository.saveImage(file)
    }
}