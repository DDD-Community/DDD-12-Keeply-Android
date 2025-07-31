package com.keeply.domain.repository

import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.model.ScanImage
import kotlinx.coroutines.flow.Flow

interface OcrRepository {
    suspend fun analyzeImage(scanImage: ScanImage): Flow<ScanAnalyze>
}