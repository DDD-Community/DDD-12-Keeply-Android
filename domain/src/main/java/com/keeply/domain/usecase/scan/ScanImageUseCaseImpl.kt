package com.keeply.domain.usecase.scan

import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.model.ScanImage
import com.keeply.domain.repository.OcrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanImageUseCaseImpl @Inject constructor(
    val repository: OcrRepository
) : ScanImageUseCase {
    override suspend fun invoke(scanImage: ScanImage): Flow<ScanAnalyze> =
        repository.analyzeImage(scanImage)
}