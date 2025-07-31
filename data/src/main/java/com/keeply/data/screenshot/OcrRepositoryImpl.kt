package com.keeply.data.screenshot

import com.keeply.data.screenshot.mapper.toDomain
import com.keeply.data.screenshot.mapper.toRequest
import com.keeply.data.screenshot.remote.OcrService
import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.model.ScanImage
import com.keeply.domain.repository.OcrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OcrRepositoryImpl @Inject constructor(
    private val ocrService: OcrService
) : OcrRepository {
    override suspend fun analyzeImage(scanImage: ScanImage): Flow<ScanAnalyze> = flow {
        emit(
            ocrService.analyzeImage(
                scanImage.toRequest()
            ).response.toDomain()
        )
    }
}