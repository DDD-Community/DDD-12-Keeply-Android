package com.keeply.domain.usecase.scan

import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.model.ScanImage
import kotlinx.coroutines.flow.Flow

interface ScanImageUseCase {
    suspend operator fun invoke(scanImage: ScanImage): Flow<ScanAnalyze>
}