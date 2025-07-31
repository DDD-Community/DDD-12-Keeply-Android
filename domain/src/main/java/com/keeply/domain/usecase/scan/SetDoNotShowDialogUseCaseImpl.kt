package com.keeply.domain.usecase.scan

import com.keeply.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetDoNotShowDialogUseCaseImpl @Inject constructor(
    private val repository: PreferencesRepository
) : SetDoNotShowDialogUseCase {
    override suspend operator fun invoke(value: Boolean) {
        repository.setDontShowDialog(value)
    }
}