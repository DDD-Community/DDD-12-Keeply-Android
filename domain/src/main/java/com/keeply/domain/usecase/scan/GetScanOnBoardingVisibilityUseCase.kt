package com.keeply.domain.usecase.scan

import kotlinx.coroutines.flow.Flow

interface GetScanOnBoardingVisibilityUseCase {
    operator fun invoke(): Flow<Boolean>
}