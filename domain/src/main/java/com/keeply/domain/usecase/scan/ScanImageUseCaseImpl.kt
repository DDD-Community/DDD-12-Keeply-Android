package com.keeply.domain.usecase.scan

import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.repository.OcrRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class ScanImageUseCaseImpl @Inject constructor(
    val repository: OcrRepository
) : ScanImageUseCase {
    override suspend fun invoke(isNew: Boolean, imageId: Long?, file: File): Flow<ScanAnalyze> =
        repository.analyzeImage(isNew, imageId, file)
}