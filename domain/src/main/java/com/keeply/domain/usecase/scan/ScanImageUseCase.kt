package com.keeply.domain.usecase.scan

import com.keeply.domain.model.ScanAnalyze
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ScanImageUseCase {
    suspend operator fun invoke(isNew: Boolean, imageId: Int?, isSkip: Boolean, file: File): Flow<ScanAnalyze>
}