package com.keeply.domain.usecase.scan

import com.keeply.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetScanOnBoardingVisibilityUseCaseImpl @Inject constructor(
    private val repository: PreferencesRepository
) : GetScanOnBoardingVisibilityUseCase {
    override operator fun invoke(): Flow<Boolean> {
        return repository.dontShowDialog.map { dontShow -> !dontShow }
    }
}