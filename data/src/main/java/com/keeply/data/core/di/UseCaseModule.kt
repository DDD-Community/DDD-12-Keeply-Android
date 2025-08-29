package com.keeply.data.core.di

import com.keeply.domain.image.repository.ImageRepository
import com.keeply.domain.image.usecase.SaveImageUseCase
import com.keeply.domain.image.usecase.SaveImageUseCaseImpl
import com.keeply.domain.repository.LocalScreenshotRepository
import com.keeply.domain.repository.OcrRepository
import com.keeply.domain.repository.PreferencesRepository
import com.keeply.domain.repository.UserRepository
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCase
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCaseImpl
import com.keeply.domain.usecase.scan.ScanImageUseCase
import com.keeply.domain.usecase.scan.ScanImageUseCaseImpl
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCase
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCaseImpl
import com.keeply.domain.usecase.screenshot.GetLocalScreenshotsUseCase
import com.keeply.domain.usecase.screenshot.GetLocalScreenshotsUseCaseImpl
import com.keeply.domain.usecase.user.GetUserInfoUseCase
import com.keeply.domain.usecase.user.GetUserInfoUseCaseImpl
import com.keeply.domain.usecase.user.LoginKakaoUseCase
import com.keeply.domain.usecase.user.LoginKakaoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginKakaoUseCase(
        userRepository: UserRepository
    ): LoginKakaoUseCase = LoginKakaoUseCaseImpl(
        userRepository = userRepository
    )

    @Provides
    @Singleton
    fun provideGetScanOnBoardingVisibilityUseCase(
        preferencesRepository: PreferencesRepository
    ): GetScanOnBoardingVisibilityUseCase = GetScanOnBoardingVisibilityUseCaseImpl(
        repository = preferencesRepository
    )

    @Provides
    @Singleton
    fun provideSetDoNotShowScanOnBoarding(
        preferencesRepository: PreferencesRepository
    ): SetDoNotShowDialogUseCase = SetDoNotShowDialogUseCaseImpl(
        repository = preferencesRepository
    )

    @Provides
    @Singleton
    fun provideGetLocalScreenshotsUseCase(
        localScreenshotRepository: LocalScreenshotRepository
    ): GetLocalScreenshotsUseCase = GetLocalScreenshotsUseCaseImpl(
        repository = localScreenshotRepository
    )

    @Provides
    @Singleton
    fun provideAnalyzeImageUseCase(
        ocrRepository: OcrRepository
    ): ScanImageUseCase = ScanImageUseCaseImpl(
        repository = ocrRepository
    )

    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(
        userRepository: UserRepository
    ): GetUserInfoUseCase = GetUserInfoUseCaseImpl(
        userRepository = userRepository
    )

    @Provides
    @Singleton
    fun provideSaveImageUseCase(
        imageRepository: ImageRepository
    ): SaveImageUseCase = SaveImageUseCaseImpl(
        imageRepository = imageRepository
    )

}