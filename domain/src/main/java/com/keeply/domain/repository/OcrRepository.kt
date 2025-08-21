package com.keeply.domain.repository

import com.keeply.domain.model.ScanAnalyze
import kotlinx.coroutines.flow.Flow
import java.io.File

interface OcrRepository {
    suspend fun analyzeImage(isNew: Boolean, imageId: Int?, isSkip: Boolean, file: File): Flow<ScanAnalyze>
}