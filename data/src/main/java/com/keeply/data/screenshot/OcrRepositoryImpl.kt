package com.keeply.data.screenshot

import com.keeply.data.screenshot.mapper.toDomain
import com.keeply.data.screenshot.mapper.toMultipartPart
import com.keeply.data.screenshot.mapper.toPlainRequestBody
import com.keeply.data.screenshot.remote.OcrService
import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.repository.OcrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class OcrRepositoryImpl @Inject constructor(
    private val ocrService: OcrService
) : OcrRepository {
    override suspend fun analyzeImage(
        isNew: Boolean,
        imageId: Long?,
        file: File
    ): Flow<ScanAnalyze> = flow {
        emit(
            ocrService.analyzeImage(
                isNew = isNew.toString().toPlainRequestBody(),
                imageId = imageId?.toString()?.toPlainRequestBody(),
                file = file.toMultipartPart()
            ).response.toDomain()
        )
    }
}