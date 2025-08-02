package com.keeply.domain.repository

import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.model.ScanImage
import kotlinx.coroutines.flow.Flow
import java.io.File

interface OcrRepository {
    suspend fun analyzeImage(isNew: Boolean, imageId: Long?, file: File): Flow<ScanAnalyze>
}