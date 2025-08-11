package com.keeply.domain.usecase.scan

import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.model.ScanImage
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ScanImageUseCase {
    suspend operator fun invoke(isNew: Boolean, imageId: Long?, file: File): Flow<ScanAnalyze>
}