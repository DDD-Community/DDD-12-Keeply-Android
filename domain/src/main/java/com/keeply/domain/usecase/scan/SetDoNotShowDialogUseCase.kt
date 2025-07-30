package com.keeply.domain.usecase.scan

interface SetDoNotShowDialogUseCase {
    suspend operator fun invoke(value: Boolean)
}